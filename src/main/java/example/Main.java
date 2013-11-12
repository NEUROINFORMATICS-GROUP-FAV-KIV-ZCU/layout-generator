package example;

import java.lang.annotation.Annotation;


public class Main {

    public static void main(String[] args) {
        try {
            Class<?> cls = Class.forName("example.pojo.Person");
            for (Annotation a : cls.getAnnotations()) {
                System.out.println(a.annotationType().getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
