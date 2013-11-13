package cz.zcu.kiv.formgen.impl;

import java.lang.reflect.Field;
import cz.zcu.kiv.formgen.annotation.Form;
import cz.zcu.kiv.formgen.annotation.FormItem;
import odml.core.Section;


public class ClassParser {
    
    
    public Section parse(Class<?> cls) {
        // TODO 
        if (!cls.isAnnotationPresent(Form.class))
            return null;  // TODO exception?
        
        Form formAnnot = cls.getAnnotation(Form.class);
        System.out.println(formAnnot.value());
        
        for (Field f : cls.getDeclaredFields()) {
            if (f.isAnnotationPresent(FormItem.class)) {
                FormItem formItemAnnot = f.getAnnotation(FormItem.class);
                System.out.println(f.getName() + " - label: " + formItemAnnot.label() + ", reguired: " + formItemAnnot.required());
            }
        }
        
        return null;
    }

}
