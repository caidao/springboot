package paner.das.strong;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.log4j.Logger;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by www-data on 16/12/27.
 */
public class MapperRefresh {
    private static Logger log = Logger.getLogger(MapperRefresh.class);

	// MyBatis配置对象
    private Configuration configuration;
    private static boolean refresh; 		// 刷新启用后，是否启动了刷新线程

    public void setConfiguration(Configuration configuration) {
         this.configuration = configuration;
    }

    public static boolean isRefresh() {
        return refresh;
    }

    public static void setRefresh(boolean refresh) {
        MapperRefresh.refresh = refresh;
    }

    /**
     * 执行刷新
     * @param className 类名（全路径）
     * @param mapperContent mapper内容
     * @throws NestedIOException 解析异常
     * @throws FileNotFoundException 文件未找到
     * @author ThinkGem
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void refresh(String className,String mapperContent) throws Exception {


        File file = MapperFromDB.getFile(className,mapperContent);
        // 获取需要刷新的Mapper文件列表

            InputStream inputStream = new FileInputStream(file);
            String resource = file.getAbsolutePath();
            try {

                // 清理原有资源，更新为自己的StrictMap方便，增量重新加载
                String[] mapFieldNames = new String[]{
                        "mappedStatements", "caches",
                        "resultMaps", "parameterMaps",
                        "keyGenerators", "sqlFragments"
                };
                for (String fieldName : mapFieldNames){
                    Field field = configuration.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);
                    Map map = ((Map)field.get(configuration));
                    if (!(map instanceof StrictMap)){
                        Map newMap = new StrictMap(StringUtils.capitalize(fieldName) + "collection");
                        for (Object key : map.keySet()){
                            try {
                                newMap.put(key, map.get(key));
                            }catch(IllegalArgumentException ex){
                                newMap.put(key, ex.getMessage());
                            }
                        }
                        field.set(configuration, newMap);
                    }
                }

                // 清理已加载的资源标识，方便让它重新加载。
                Field loadedResourcesField = configuration.getClass().getDeclaredField("loadedResources");
                loadedResourcesField.setAccessible(true);
                Set loadedResourcesSet = ((Set)loadedResourcesField.get(configuration));
                loadedResourcesSet.remove(resource);

                //重新编译加载资源文件。
                XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(inputStream, configuration,
                        resource, configuration.getSqlFragments());
                xmlMapperBuilder.parse();
            } catch (Exception e) {
                throw new NestedIOException("Failed to parse mapping resource: '" + resource + "'", e);
            } finally {
                ErrorContext.instance().reset();
            }
            //System.out.println("Refresh file: " + mappingPath + StringUtils.substringAfterLast(fileList.get(i).getAbsolutePath(), mappingPath));
            if (log.isDebugEnabled()) {
                log.debug("Refresh file: " + file.getAbsolutePath());
                log.debug("Refresh filename: " + file.getName());
            }

    }


    /**
     * 重写 org.apache.ibatis.session.Configuration.StrictMap 类
     * 来自 MyBatis3.4.0版本，修改 put 方法，允许反复 put更新。
     */
    public static class StrictMap<V> extends HashMap<String, V> {

        private static final long serialVersionUID = -4950446264854982944L;
        private String name;

        public StrictMap(String name, int initialCapacity, float loadFactor) {
            super(initialCapacity, loadFactor);
            this.name = name;
        }

        public StrictMap(String name, int initialCapacity) {
            super(initialCapacity);
            this.name = name;
        }

        public StrictMap(String name) {
            super();
            this.name = name;
        }

        public StrictMap(String name, Map<String, ? extends V> m) {
            super(m);
            this.name = name;
        }

        @SuppressWarnings("unchecked")
        public V put(String key, V value) {
            // ThinkGem 如果现在状态为刷新，则刷新(先删除后添加)
            if (MapperRefresh.isRefresh()) {
                remove(key);
                MapperRefresh.log.debug("refresh key:" + key.substring(key.lastIndexOf(".") + 1));
            }
            // ThinkGem end
            if (containsKey(key)) {
                throw new IllegalArgumentException(name + " already contains value for " + key);
            }
            if (key.contains(".")) {
                final String shortKey = getShortName(key);
                if (super.get(shortKey) == null) {
                    super.put(shortKey, value);
                } else {
                    super.put(shortKey, (V) new Ambiguity(shortKey));
                }
            }
            return super.put(key, value);
        }

        public V get(Object key) {
            V value = super.get(key);
            if (value == null) {
                throw new IllegalArgumentException(name + " does not contain value for " + key);
            }
            if (value instanceof Ambiguity) {
                throw new IllegalArgumentException(((Ambiguity) value).getSubject() + " is ambiguous in " + name
                        + " (try using the full name including the namespace, or rename one of the entries)");
            }
            return value;
        }

        private String getShortName(String key) {
            final String[] keyparts = key.split("\\.");
            return keyparts[keyparts.length - 1];
        }

        protected static class Ambiguity {
            private String subject;

            public Ambiguity(String subject) {
                this.subject = subject;
            }

            public String getSubject() {
                return subject;
            }
        }
    }

}
