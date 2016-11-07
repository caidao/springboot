package reflect.demo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by www-data on 16/11/1.
 */
public class MethodObject {

    private String temp;

    protected String tss;

    public String test(String arg1,int arg2){
        return arg1+","+arg2;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {

        Class aClass = MethodObject.class;
        Method[] methods = aClass.getMethods();
        for (Method method:methods){
            if ("test".equals(method.getName()))
                System.out.println(method.invoke(new MethodObject(),"test", 2314).toString());
        }

    }
}
