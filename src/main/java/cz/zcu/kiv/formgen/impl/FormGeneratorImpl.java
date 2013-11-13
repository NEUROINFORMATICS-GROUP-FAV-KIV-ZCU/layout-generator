package cz.zcu.kiv.formgen.impl;

import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import cz.zcu.kiv.formgen.FormGenerator;
import cz.zcu.kiv.formgen.annotation.Form;
import cz.zcu.kiv.formgen.annotation.FormItem;


public class FormGeneratorImpl implements FormGenerator {

    public void loadClass(String name) throws ClassNotFoundException {
        Class<?> cls = Class.forName(name);
        
        if (!cls.isAnnotationPresent(Form.class))
            return;  // TODO exception?
        
        Form formAnnot = cls.getAnnotation(Form.class);
        System.out.println(formAnnot.value());
        
        for (Field f : cls.getDeclaredFields()) {
            if (f.isAnnotationPresent(FormItem.class)) {
                FormItem formItemAnnot = f.getAnnotation(FormItem.class);
                System.out.println(f.getName() + " - label: " + formItemAnnot.label() + ", reguired: " + formItemAnnot.required());
            }
        }
    }


    public void loadPackage(String name) {
        // TODO Auto-generated method stub

    }


    public void writeLayout(OutputStream out) {
        // TODO Auto-generated method stub

    }

}
