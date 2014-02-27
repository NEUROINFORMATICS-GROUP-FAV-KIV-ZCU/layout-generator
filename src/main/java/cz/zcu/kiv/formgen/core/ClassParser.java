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
import cz.zcu.kiv.formgen.ModelProvider;
import cz.zcu.kiv.formgen.annotation.FormDescription;
import cz.zcu.kiv.formgen.annotation.FormItemRestriction;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.model.FormItem;
import cz.zcu.kiv.formgen.model.FormItemContainer;


/**
 * Parses Java classes using reflection and creates an appropriate model.
 *
 * @author Jakub Krauz
 */
public class ClassParser extends AbstractParser<Class<?>> {
    
    
    /**
     * Constructor. 
     * 
     * @param modelProvider object implementing the {@link ModelProvider} interface
     */
    public ClassParser(ModelProvider modelProvider) {
        super(modelProvider);
    }
    
    
    
    /**
     * Parses the given class and creates a new {@link Form} model.
     * 
     * @param cls the Java class to be parsed
     * @return the newly created {@link Form} object
     */
    @Override
    public Form parse(Class<?> cls) {
        Form form = super.parse(cls);
        form.setLayoutName(form.getFormName() + "-generated");
        return form;
    }
    
    
    
    /**
     * Parses the given class and adds it as a subform to an existing {@link Form} model
     * (used for multiple-class forms).
     * 
     * @param cls the Java class to be parsed
     * @param form the model in which the parsed class is to be added
     * @return the updated form model
     */
    @Override
    public void parse(Class<?> cls, Form form) {
        form.addItem(_parse(cls, form.highestItemId() + 1));
    }
    
    
    
    /**
     * Creates a multi-form object defined by the annotation.
     * 
     * @param annotation the {@link cz.zcu.kiv.formgen.annotation.MultiForm MultiForm} annotation
     * @return new form defined by the annotation
     */
    @Override
    public Form createMultiform(cz.zcu.kiv.formgen.annotation.MultiForm annotation) {
        Form form = super.createMultiform(annotation);
        form.setId(0);
        form.setLayoutName(annotation.value() + "-generated");
        return form;
    }
    
    
    
    @Override
    protected Form _parse(Class<?> cls) {
        return _parse(cls, 0);
    }
    
    
    
    /**
     * Recursively parses the given class and its members and adds them to an existing {@link Form} model.
     * 
     * @param cls the Java class to be parsed
     * @param form the model in which the parsed class is to be added
     * @return the updated form model
     */
    protected Form _parse(Class<?> cls, int id) {
        Form form = createForm(cls);
        form.setId(id++);
        
        for (Field f : formItemFields(cls)) {
            if (isSimpleType(f.getType())) {
                FormItem item = createFormField(f);
                item.setId(id++);
                form.addItem(item);
            }
            else if (Collection.class.isAssignableFrom(f.getType())) {
                FormItemContainer set = createFormSet(f, id++);
                form.addItem(set);
                id = set.getId() + 1;
            } else {
                Form subform = _parse(f.getType(), id++);
                form.addItem(subform);
                id = subform.highestItemId() + 1;
            }
        }
        
        return form;
    }
    
    
    
    /**
     * Creates a new {@link Form} representing the given class using the {@link ModelProvider} object. 
     * The provider object is set in the constructor (see {@link #ClassParser(ModelProvider)}.
     * 
     * @param cls the Java class representing the form to be created
     * @return the newly created form
     */
    @Override
    protected Form createForm(Class<?> cls) {
        Form form = super.createForm(cls);
        
        String definition = null;
        if (cls.isAnnotationPresent(FormDescription.class)) {
            FormDescription description = cls.getAnnotation(FormDescription.class);
            definition = description.value();
        }
        form.setDescription(definition);
        
        return form;
    }
    
    
    
    /**
     * Creates a new {@link FormField} representing the given field using the {@link ModelProvider} object. 
     * The provider object is set in the constructor (see {@link #ClassParser(ModelProvider)}.
     * 
     * @param field the Java field representing the form item to be created
     * @return the newly created form item object
     */
    protected FormItem createFormField(Field field) {
        FormField formField = formProvider.newFormField(field.getName(), field.getType());
        
        cz.zcu.kiv.formgen.annotation.FormItem formItemAnnot = field.getAnnotation(cz.zcu.kiv.formgen.annotation.FormItem.class);
        String label = formItemAnnot.label();
        formField.setLabel(label.isEmpty() ? field.getName() : label);
        formField.setRequired(formItemAnnot.required());
        
        if (field.isAnnotationPresent(FormItemRestriction.class)) {
            FormItemRestriction restriction = field.getAnnotation(FormItemRestriction.class);
            if (restriction.minLength() != -1)
                formField.setMinLength(restriction.minLength());
            if (restriction.maxLength() != -1)
                formField.setMaxLength(restriction.maxLength());
            if (!Double.isNaN(restriction.minValue())) {
                if (isIntegerType(field.getType()))
                    formField.setMinValue((int) restriction.minValue());
                else
                    formField.setMinValue(restriction.minValue());
            }
            if (!Double.isNaN(restriction.maxValue())) {
                if (isIntegerType(field.getType()))
                    formField.setMaxValue((int) restriction.maxValue());
                else
                    formField.setMaxValue(restriction.maxValue());
            }
            if (!restriction.defaultValue().isEmpty())
                formField.setDefaultValue(restriction.defaultValue());
            if (restriction.values().length > 1)
                formField.setPossibleValues(restriction.values());
        }
        
        return formField;
    }
    
    
    
    /**
     * <p>Creates a new {@link FormItemContainer} representing the given field (that must be a collection)
     * using the {@link ModelProvider} object. The provider object is set in the constructor 
     * (see {@link #ClassParser(ModelProvider)}.</p>
     * 
     * <p>Note that the Collection should be parameterized so as the method can determine items
     * of the collection. Otherwise null is returned.</p>
     * 
     * @param field the Java field (of Collection type) representing the set to be created
     * @param id the ID assigned to the new form set
     * @return the newly created set object, or null if the collection is not parameterized
     */
    private FormItemContainer createFormSet(Field field, int id) {
        FormItemContainer formSet = null;
        
        if (field.getGenericType() instanceof ParameterizedType) {
            
            ParameterizedType type = (ParameterizedType) field.getGenericType();
            Type[] parameters = type.getActualTypeArguments();
            
            if (parameters.length == 1 && parameters[0] instanceof Class) {
                formSet = formProvider.newFormSet(field.getName(), field.getType());
                cz.zcu.kiv.formgen.annotation.FormItem formItemAnnot = field.getAnnotation(cz.zcu.kiv.formgen.annotation.FormItem.class);
                String label = formItemAnnot.label();
                formSet.setLabel(label.isEmpty() ? field.getName() : label);
                formSet.setRequired(formItemAnnot.required());

                Class<?> clazz = (Class<?>) parameters[0];
                if (isSimpleType(clazz)) {
                    FormField formField = formProvider.newFormField(clazz.getSimpleName(), clazz);
                    formField.setId(id);
                    formSet.addItem(formField);
                    formSet.setId(id + 1);
                } else {
                    Form form = _parse(clazz, id);
                    formSet.addItem(form);
                    formSet.setId(form.highestItemId() + 1);
                }
            }
            
        } else {
            System.out.println("Warning: non-parameterized collection");
        }
        
        return formSet;
    }
    

}
