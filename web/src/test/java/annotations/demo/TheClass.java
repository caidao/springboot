package annotations.demo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.StandardSocketOptions;

/**
 * Created by www-data on 16/10/31.
 */

@MyAnnotaion(name = "test",value = "hello word")
public class TheClass {

    @FieldAnnoation(name = "field",vlaue = "what")
    private String myField =null;

    @MethodAnnotation(name = "methodTest",value = "getInfo")
    public String getInfo(){
        return "test information";
    }

    public  void getParameter(@ParamAnnotation(name="test",value = "test")String parma){

    }

    public static  void main(String[] args){
        Class aclass = TheClass.class;
        Annotation[] annotations = aclass.getAnnotations();


        //类注解
        for (Annotation annotation:annotations){
            if (annotation instanceof  MyAnnotaion){
                MyAnnotaion myAnnotaion = (MyAnnotaion) annotation;
                System.out.println("name: "+ myAnnotaion.name());
                System.out.println("value: "+ myAnnotaion.value());
            }
        }

        //方法注解
       for (Method method :  aclass.getMethods()){
           Annotation[] methodAnnotations = method.getDeclaredAnnotations();
           for (Annotation annotation:methodAnnotations){
               if (annotation instanceof  MethodAnnotation){
                   MethodAnnotation myAnnotaion = (MethodAnnotation) annotation;
                   System.out.println("name: "+ myAnnotaion.name());
                   System.out.println("value: "+ myAnnotaion.value());
               }
           }

           //方法参数注解
           int i=0;
           Annotation[][] parameterAnnotations = method.getParameterAnnotations();
           Class[] parameterTypes = method.getParameterTypes();
           for(Annotation[] pannotations : parameterAnnotations){
               Class parameterType = parameterTypes[i++];
               for (Annotation annotation:pannotations){
                   if (annotation instanceof ParamAnnotation){
                       ParamAnnotation paramAnnotation = (ParamAnnotation) annotation;
                       System.out.println("param:"+parameterType.getName());
                       System.out.println("name:"+ paramAnnotation.name());
                       System.out.println("value:"+paramAnnotation.value());
                   }
               }
           }

       }

        //类变量注解
        for (Field field: aclass.getDeclaredFields()){
            Annotation[] annotations1 = field.getDeclaredAnnotations();
            for (Annotation annotation:annotations1){
                if (annotation instanceof FieldAnnoation){
                    FieldAnnoation fieldAnnoation = (FieldAnnoation) annotation;
                    System.out.println("name: "+fieldAnnoation.name());
                    System.out.println("value: "+fieldAnnoation.vlaue());
                }
            }
        }


    }
}
