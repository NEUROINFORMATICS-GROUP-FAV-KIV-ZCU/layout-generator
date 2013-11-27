/***********************************************************************************************************************
 *
 * This file is part of the layout-generator project
 *
 * ==========================================
 *
 * Copyright (C) 2013 by University of West Bohemia (http://www.zcu.cz/en/)
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 ***********************************************************************************************************************
 *
 * ClassParser.java, 15. 11. 2013 17:36:16 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml;

import java.lang.reflect.Field;
import java.util.Date;
import cz.zcu.kiv.formgen.FormNotFoundException;
import cz.zcu.kiv.formgen.annotation.Form;
import cz.zcu.kiv.formgen.annotation.FormDescription;
import cz.zcu.kiv.formgen.annotation.FormItem;
import odml.core.Section;


/**
 *
 * @author Jakub Krauz
 */
public class ClassParser {
    
    
    public Section parse(Class<?> cls) throws FormNotFoundException {
        if (!cls.isAnnotationPresent(Form.class))
            throw new FormNotFoundException();
        
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
        
        String name;
        if (cls.isAnnotationPresent(Form.class)) {
            Form form = cls.getAnnotation(Form.class);
            name = form.value();
        } else {
            name = cls.getSimpleName();
        }
        
        String definition = null;
        if (cls.isAnnotationPresent(FormDescription.class)) {
            FormDescription description = cls.getAnnotation(FormDescription.class);
            if (name.equals(description.form()))
                definition = description.text();
        }
        
        try {
            section = new Section(name, SectionType.FORM.getValue());
            section.addProperty("label", name);
            section.setDefinition(definition);
        } catch (Exception e) {
            // exception is thrown only in case of null or empty type argument in new Section()
            e.printStackTrace();
        }
        
        return section;
    }
    
    
    
    private Section createItemSection(Field field) {
        Section section = null;
        
        if (!isSimpleType(field.getType())) {
            section = _parse(field.getType());
        } else try {
            section = new Section(field.getName(), mapType(field.getType()));
            FormItem formItem = field.getAnnotation(FormItem.class);
            String label = formItem.label();
            section.addProperty("label", label.isEmpty() ? field.getName() : label);
            section.addProperty("required", formItem.required());
        } catch (Exception e) {
            // exception is thrown only in case of null or empty type argument in new Section()
            e.printStackTrace();
        }
        
        return section;
    }
    
    
    
    // TODO vice jednoduchych typu
    private boolean isSimpleType(Class<?> type) {
        if (type.isPrimitive())
            return true;
        else if (type.equals(String.class))
            return true;
        else if (type.equals(Date.class))
            return true;
        else
            return false;
    }
    
    
    
    private String mapType(Class<?> type) {        
        if (!isSimpleType(type))
            return SectionType.FORM.getValue();
        else if (type.equals(boolean.class) || type.equals(Boolean.class))
            return SectionType.CHECKBOX.getValue();
        else
            return SectionType.TEXTBOX.getValue();
    }
    
    

}
