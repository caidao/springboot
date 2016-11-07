package reflect.demo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by www-data on 16/11/1.
 */

//http://wiki.jikexueyuan.com/project/java-reflection/java-type.html
public class GenericTypeObject {

    private List<String> pStringList = null;

    protected List<String> stringList = null;


    public List<String>getStringList(){
        return this.stringList;
    }

    //动态获取泛型的具体对象类型
    public static void  main(String[] args) throws NoSuchMethodException, NoSuchFieldException {

        Method method = GenericTypeObject.class.getMethod("getStringList",null);
        Type returnType = method.getGenericReturnType();

        //获取返回方法中泛型的类型
        if (returnType instanceof ParameterizedType){
            ParameterizedType type = (ParameterizedType) returnType;
            Type[] types = type.getActualTypeArguments();
            for (Type type1:types){
                Class typeClass = (Class) type1;
                System.out.println("typeClass = "+typeClass);
            }
        }

        //获取public类型的中泛型的类型
        Field field =  GenericTypeObject.class.getDeclaredField("pStringList");
        Type genericFieldType = field.getGenericType();
        if (genericFieldType instanceof ParameterizedType){
            ParameterizedType aType = (ParameterizedType) genericFieldType;
            Type[] fieldArgTypes = aType.getActualTypeArguments();
            for (Type fieldType : fieldArgTypes){
                Class fieldArgClass = (Class) fieldType;
                System.out.println("fieldArgClass = "+fieldArgClass);
            }
        }
    }
}
