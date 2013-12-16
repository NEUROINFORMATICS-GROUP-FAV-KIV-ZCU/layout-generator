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

package cz.zcu.kiv.formgen.core;

import java.lang.reflect.Field;
import cz.zcu.kiv.formgen.Form;
import cz.zcu.kiv.formgen.FormItem;
import cz.zcu.kiv.formgen.FormNotFoundException;
import cz.zcu.kiv.formgen.annotation.FormDescription;


/**
 *
 * @author Jakub Krauz
 */
public class ClassParser {
    
    private FormProvider formProvider;
    
    
    public ClassParser(FormProvider formProvider) {
        this.formProvider = formProvider;
    }
    
    
    public Form parse(Class<?> cls) throws FormNotFoundException {
        if (!cls.isAnnotationPresent(cz.zcu.kiv.formgen.annotation.Form.class))
            throw new FormNotFoundException();
        
        return _parse(cls, createForm(cls));
    }
    
    
    public Form parse(Class<?> cls, Form form) throws FormNotFoundException {
        if (!cls.isAnnotationPresent(cz.zcu.kiv.formgen.annotation.Form.class))
            throw new FormNotFoundException();
        
        return _parse(cls, form);
    }
    
    
    private Form _parse(Class<?> cls, Form form) {
        for (Field f : cls.getDeclaredFields()) {
            if (f.isAnnotationPresent(cz.zcu.kiv.formgen.annotation.FormItem.class)) {
                if (!isSimpleType(f.getType()))
                    form.addSubform(_parse(f.getType(), createForm(f.getType())));
                else 
                    form.addItem(createItem(f));
            }
        }
        
        return form;
    }
    
    
    
    private Form createForm(Class<?> cls) {        
        String name;
        if (cls.isAnnotationPresent(cz.zcu.kiv.formgen.annotation.Form.class)) {
            cz.zcu.kiv.formgen.annotation.Form formAnnot = cls.getAnnotation(cz.zcu.kiv.formgen.annotation.Form.class);
            name = formAnnot.value();
        } else {
            name = cls.getSimpleName();
        }
        
        String definition = null;
        if (cls.isAnnotationPresent(FormDescription.class)) {
            FormDescription description = cls.getAnnotation(FormDescription.class);
            definition = description.value();
        }
        
        Form form = formProvider.newForm(name);
        form.setDescription(definition);
        
        return form;
    }
    
    
    
    private FormItem createItem(Field field) {
        FormItem formItem = formProvider.newFormItem(field.getName(), field.getType());            
        cz.zcu.kiv.formgen.annotation.FormItem formItemAnnot = field.getAnnotation(cz.zcu.kiv.formgen.annotation.FormItem.class);
        String label = formItemAnnot.label();
        formItem.setLabel(label.isEmpty() ? field.getName() : label);
        formItem.setRequired(formItemAnnot.required());
        
        return formItem;
    }
    
    
    
    private boolean isSimpleType(Class<?> type) {
        return formProvider.typeMapper().isSimpleType(type);
    }
    

}
