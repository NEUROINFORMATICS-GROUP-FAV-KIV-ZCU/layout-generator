package example;

import cz.zcu.kiv.formgen.FormGenerator;
import cz.zcu.kiv.formgen.impl.FormGeneratorImpl;


public class Main {

    public static void main(String[] args) {
        FormGenerator gen = new FormGeneratorImpl();
        try {
            gen.loadClass("example.pojo.Person");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
