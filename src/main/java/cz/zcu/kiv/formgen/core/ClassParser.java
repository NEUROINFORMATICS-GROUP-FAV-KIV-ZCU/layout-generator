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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import cz.zcu.kiv.formgen.Form;
import cz.zcu.kiv.formgen.FormItem;
import cz.zcu.kiv.formgen.FormNotFoundException;
import cz.zcu.kiv.formgen.FormSet;
import cz.zcu.kiv.formgen.annotation.FormDescription;
import cz.zcu.kiv.formgen.annotation.FormItemRestriction;


/**
 * Parses Java classes using reflection and creates appropriate model.
 *
 * @author Jakub Krauz
 */
public class ClassParser {
    
    
    /** Object used to create new {@link Form} and {@link FormItem} objects. */
    private FormProvider formProvider;
    
    /** Last ID assigned to a form or its item. */
    private int lastId = 0;
    
    
    
    /**
     * Constructor. 
     * 
     * @param formProvider object implementing the FormProvider interface
     */
    public ClassParser(FormProvider formProvider) {
        this.formProvider = formProvider;
    }
    
    
    
    /**
     * Parses the given class and adds it to a newly created {@link Form} model.
     * 
     * @param cls the Java class to be parsed
     * @return the newly created {@link Form} object
     * @throws FormNotFoundException if the {@link cz.zcu.kiv.formgen.annotation.Form Form} annotation was not 
     *                                present over the given class object
     */
    public Form parse(Class<?> cls) throws FormNotFoundException {
        if (!cls.isAnnotationPresent(cz.zcu.kiv.formgen.annotation.Form.class))
            throw new FormNotFoundException();
        
        return _parse(cls, createForm(cls));
    }
    
    
    
    /**
     * Parses the given class and adds it to an existing {@link Form} model.
     * 
     * @param cls the Java class to be parsed
     * @param form the model in which the parsed class is to be added
     * @return the updated form model
     * @throws FormNotFoundException if the {@link cz.zcu.kiv.formgen.annotation.Form Form} annotation was not 
     *                                present over the given class object
     */
    public Form parse(Class<?> cls, Form form) throws FormNotFoundException {
        if (!cls.isAnnotationPresent(cz.zcu.kiv.formgen.annotation.Form.class))
            throw new FormNotFoundException();
        
        return _parse(cls, form);
    }
    
    
    
    /**
     * Recursively parses the given class and its members and adds them to an existing {@link Form} model.
     * 
     * @param cls the Java class to be parsed
     * @param form the model in which the parsed class is to be added
     * @return the updated form model
     */
    private Form _parse(Class<?> cls, Form form) {
        
        for (Field f : cls.getDeclaredFields()) {
            if (f.isAnnotationPresent(cz.zcu.kiv.formgen.annotation.FormItem.class)) {
                if (isSimpleType(f.getType()))
                    form.addItem(createItem(f));
                else if (Collection.class.isAssignableFrom(f.getType()))
                    form.addSet(createSet(f));
                else 
                    form.addSubform(_parse(f.getType(), createForm(f.getType())));
            }
        }
        
        return form;
    }
    
    
    
    
    /**
     * Creates a new {@link Form} representing the given class using the {@link FormProvider} object. 
     * The provider object is set in the constructor (see {@link #ClassParser(FormProvider)}.
     * 
     * @param cls the Java class representing the form to be created
     * @return the newly created form
     */
    private Form createForm(Class<?> cls) {        
        String name;
        if (cls.isAnnotationPresent(cz.zcu.kiv.formgen.annotation.Form.class)) {
            name = cls.getAnnotation(cz.zcu.kiv.formgen.annotation.Form.class).value();
            if (name.isEmpty())
                name = cls.getSimpleName();
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
        form.setId(++lastId);
        
        return form;
    }
    
    
    
    /**
     * Creates a new {@link FormItem} representing the given field using the {@link FormProvider} object. 
     * The provider object is set in the constructor (see {@link #ClassParser(FormProvider)}.
     * 
     * @param field the Java field representing the form item to be created
     * @return the newly created form item object
     */
    private FormItem createItem(Field field) {
        FormItem formItem = formProvider.newFormItem(field.getName(), field.getType());
        formItem.setId(++lastId);
        
        cz.zcu.kiv.formgen.annotation.FormItem formItemAnnot = field.getAnnotation(cz.zcu.kiv.formgen.annotation.FormItem.class);
        String label = formItemAnnot.label();
        formItem.setLabel(label.isEmpty() ? field.getName() : label);
        formItem.setRequired(formItemAnnot.required());
        
        if (field.isAnnotationPresent(FormItemRestriction.class)) {
            FormItemRestriction restriction = field.getAnnotation(FormItemRestriction.class);
            if (restriction.minLength() != -1)
                formItem.setMinLength(restriction.minLength());
            if (restriction.maxLength() != -1)
                formItem.setMaxLength(restriction.maxLength());
        }
        
        return formItem;
    }
    
    
    
    /**
     * <p>Creates a new {@link FormSet} representing the given field (that must be a collection)
     * using the {@link FormProvider} object. The provider object is set in the constructor 
     * (see {@link #ClassParser(FormProvider)}.</p>
     * 
     * <p>Note that the Collection should be parameterized so as the method can determine items
     * of the collection. Otherwise null is returned.</p>
     * 
     * @param field the Java filed (of Collection type) representing the set to be created
     * @return the newly created set object, or null if the collection is not parameterized
     */
    private FormSet createSet(Field field) {
        FormSet formSet = null;
        
        if (field.getGenericType() instanceof ParameterizedType) {
            
            ParameterizedType type = (ParameterizedType) field.getGenericType();
            Type[] parameters = type.getActualTypeArguments();
            
            if (parameters.length == 1 && parameters[0] instanceof Class) {
                formSet = formProvider.newFormSet(field.getName(), field.getType());
                cz.zcu.kiv.formgen.annotation.FormItem formItemAnnot = field.getAnnotation(cz.zcu.kiv.formgen.annotation.FormItem.class);
                String label = formItemAnnot.label();
                formSet.setLabel(label.isEmpty() ? field.getName() : label);
                formSet.setRequired(formItemAnnot.required());
                formSet.setId(++lastId);

                Class<?> clazz = (Class<?>) parameters[0];
                if (isSimpleType(clazz)) {
                    formSet.setContent(formProvider.newFormItem(clazz.getSimpleName(), clazz));
                } else {
                    formSet.setContent(_parse(clazz, createForm(clazz)));
                }
            }
            
        } else {
            System.out.println("Warning: non-parameterized collection");
        }
        
        return formSet;
    }
    
    
    
    /**
     * Determines whether the given type is considered a simple type in the given model using
     * the {@link FormProvider} object. The provider object is set in the constructor 
     * (see {@link #ClassParser(FormProvider)}.
     * 
     * @param type the Java type
     * @return true if the type is considered simple in the given model, false otherwise
     */
    private boolean isSimpleType(Class<?> type) {
        return formProvider.typeMapper().isSimpleType(type);
    }
    

}
