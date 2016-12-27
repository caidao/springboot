package paner.das.strong;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by www-data on 16/12/22.
 */
public class MapperFromDB {

    private JdbcTemplate jdbcTemplate;
    private static final Log LOGGER = LogFactory.getLog(MapperFromDB.class);

    public Resource[] buildMapperFile(DataSource dataSource){
        if (jdbcTemplate==null){
            jdbcTemplate = new JdbcTemplate(dataSource);
        }
        String sql = "select class_name,mapper_content from tbl_class_mapper";
        List<Map<String,Object>> retList = jdbcTemplate.queryForList(sql);
        if(retList==null){
            return new Resource[]{};
        }
        List<Resource> resourceList = new ArrayList<>();
        for (Map<String,Object> map:retList){
            File file = getFile(map.get("class_name").toString(),map.get("mapper_content").toString());
            Resource resource = new FileSystemResource(file);
            if(resource!=null){
                resourceList.add(resource);
            }
        }
        return resourceList.toArray(new Resource[resourceList.size()]);

    }

    public static File getFile(String className,String content){
        String[] names = className.toString().split("\\.");

        String path = MapperFromDB.class.getResource("/").getPath()+""+names[names.length-1]+".xml";
        File file =new File(path);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes());
            outputStream.close();
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e);
        }

        return file;
    }

    public static void main(String[] args){
        MapperFromDB mapperFromDB = new MapperFromDB();
        File file = mapperFromDB.getFile(MapperFromDB.class.getResource("/").getPath()+"mapper/test.xml","test");
        Resource resource = new FileSystemResource(file);
        System.out.println(resource.getFilename());
    }
}
