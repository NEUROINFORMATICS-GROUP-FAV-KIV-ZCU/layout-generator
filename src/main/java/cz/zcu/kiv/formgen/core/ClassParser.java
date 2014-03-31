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
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cz.zcu.kiv.formgen.annotation.FormDescription;
import cz.zcu.kiv.formgen.annotation.FormItemRestriction;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.model.FormItem;
import cz.zcu.kiv.formgen.model.FormItemContainer;
import cz.zcu.kiv.formgen.model.FormSet;


/**
 * Parses Java classes using reflection and creates an appropriate model.
 *
 * @author Jakub Krauz
 */
public class ClassParser extends AbstractParser<Class<?>> {
    
    /** Logger. */
    final Logger logger = LoggerFactory.getLogger(ClassParser.class);
    

    
    /**
     * Parses the given class and creates a new {@link Form} model.
     * 
     * @param cls the Java class to be parsed
     * @return the newly created {@link Form} object
     */
    @Override
    public Form parse(Class<?> cls) {
        Form form = _parse(cls, cls.getSimpleName(), 0);
        form.setLayout(true);
        form.setLayoutName(form.getName() + "-generated");
        return form;
    }
    
    
    
    /**
     * Parses the given class and adds it as a subform to an existing {@link Form} model
     * (used for multiple-class forms).
     * 
     * @param cls the Java class to be parsed
     * @param form the model in which the parsed class is to be added
     */
    @Override
    public void parse(Class<?> cls, Form form) {
        form.addItem(_parse(cls, cls.getSimpleName(), form.highestItemId() + 1));
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
        if (!annotation.label().isEmpty())
            form.setLabel(annotation.label());
        return form;
    }
    
    
    
    /**
     * Recursively parses the given class and its members and adds them to an existing {@link Form} model.
     * 
     * @param cls the Java class to be parsed
     * @param form the model in which the parsed class is to be added
     * @return the updated form model
     */
    protected Form _parse(Class<?> cls, String formName, int id) {
        Form form = createForm(formName, cls);
        form.setId(id++);
        
        for (Field f : formItemFields(cls)) {
            if (mapper.isSimpleType(f.getType())) {
                FormItem item = createFormField(f);
                item.setId(id++);
                form.addItem(item);
            }
            else if (Collection.class.isAssignableFrom(f.getType())) {
                FormItemContainer set = createFormSet(f, id++);
                form.addItem(set);
                id = set.getId() + 1;
            } else {
                Form subform = _parse(f.getType(), f.getName(), id++);
                cz.zcu.kiv.formgen.annotation.FormItem annotation = 
                        f.getAnnotation(cz.zcu.kiv.formgen.annotation.FormItem.class);
                if (!annotation.label().isEmpty())
                    subform.setLabel(annotation.label());
                subform.setRequired(annotation.required());
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
    protected Form createForm(String name, Class<?> cls) {
        Form form = new Form(name);
        form.setDataReference(cls.getName());
        
        String label = cls.getSimpleName();
        if (cls.isAnnotationPresent(cz.zcu.kiv.formgen.annotation.Form.class)) {
            cz.zcu.kiv.formgen.annotation.Form annot = cls.getAnnotation(cz.zcu.kiv.formgen.annotation.Form.class);
            if (!annot.label().isEmpty())
                label = annot.label();
        }
        form.setLabel(label);        
        
        if (cls.isAnnotationPresent(FormDescription.class)) {
            FormDescription description = cls.getAnnotation(FormDescription.class);
            form.setDescription(description.value());
        }
        
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
        FormField formField = new FormField(field.getName(), mapper.mapType(field.getType()),
                mapper.mapDatatype(field.getType()));
        
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
                if (TypeMapper.isIntegerType(field.getType()))
                    formField.setMinValue((int) restriction.minValue());
                else
                    formField.setMinValue(restriction.minValue());
            }
            if (!Double.isNaN(restriction.maxValue())) {
                if (TypeMapper.isIntegerType(field.getType()))
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
    protected FormItemContainer createFormSet(Field field, int id) {
        
        // check whether the collection type is parameterized
        if (!(field.getGenericType() instanceof ParameterizedType)) {
            logger.warn("Can not parse non-parameterized collection \"{}\"", field.getName());
            return null;
        }
            
        Class<?> clazz = Utils.genericParameter((ParameterizedType) field.getGenericType());
        FormItemContainer formSet = new FormSet(field.getName(), mapper.mapType(clazz));
        cz.zcu.kiv.formgen.annotation.FormItem formItemAnnot = field.getAnnotation(cz.zcu.kiv.formgen.annotation.FormItem.class);
        String label = formItemAnnot.label();
        formSet.setLabel(label.isEmpty() ? field.getName() : label);
        formSet.setRequired(formItemAnnot.required());

        // parse the content type
        if (mapper.isSimpleType(clazz)) {
            FormField formField = new FormField(clazz.getSimpleName(), mapper.mapType(clazz),
                    mapper.mapDatatype(clazz));
            formField.setId(id);
            formSet.addItem(formField);
            formSet.setId(id + 1);
        } else {
            Form form = _parse(clazz, clazz.getSimpleName(), id);
            formSet.addItem(form);
            formSet.setId(form.highestItemId() + 1);
        }
        
        return formSet;
    }
    

}
