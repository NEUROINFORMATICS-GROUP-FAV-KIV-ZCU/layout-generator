package cz.zcu.kiv.formgen.impl;

import java.lang.reflect.Field;
import cz.zcu.kiv.formgen.annotation.Form;
import cz.zcu.kiv.formgen.annotation.FormItem;
import odml.core.Section;


public class ClassParser {
    
    
    public Section parse(Class<?> cls) {
        if (!cls.isAnnotationPresent(Form.class))
            return null;  // TODO exception?
        
        return _parse(cls);
    }
    
    
    
    private Section _parse(Class<?> cls) {
        Section formSection = createFormSection(cls);
        
        for (Field f : cls.getDeclaredFields()) {
            if (f.isAnnotationPresent(FormItem.class)) {
                formSection.add(createItemSection(f));
            }
        }
        
        return formSection;
    }
    
    
    
    private Section createFormSection(Class<?> cls) {        
        Section section = null;
        
        try {
            section = new Section(cls.getSimpleName(), "form");
            Form form = cls.getAnnotation(Form.class);
            String label = form.value();
            section.addProperty("label", label.isEmpty() ? cls.getSimpleName() : label);
        } catch (Exception e) {
            // exception is thrown only in case of null or empty type argument in new Section()
        }
        
        return section;
    }
    
    
    
    private Section createItemSection(Field field) {
        Section section = null;
        
        if (!isSimpleType(field.getType())) {
            section = _parse(field.getType());
        } else try {
            section = new Section(field.getName(), "item"); // TODO argument type podle datoveho typu
            FormItem formItem = field.getAnnotation(FormItem.class);
            String label = formItem.label();
            section.addProperty("label", label.isEmpty() ? field.getName() : label);
            section.addProperty("required", formItem.required());
        } catch (Exception e) {
            // exception is thrown only in case of null or empty type argument in new Section()
        }
        
        return section;
    }
    
    
    
    // TODO vice jednoduchych typu (napr. Date)
    private boolean isSimpleType(Class<?> type) {
        if (type.isPrimitive())
            return true;
        else if (type.equals(String.class))
            return true;
        else
            return false;
    }
    
    

}
