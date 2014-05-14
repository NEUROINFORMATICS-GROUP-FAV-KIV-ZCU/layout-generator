/***********************************************************************************************************************
 * 
 * This file is part of the layout-generator project
 * 
 * =================================================
 * 
 * Copyright (C) 2013 by University of West Bohemia (http://www.zcu.cz/en/)
 * 
 *********************************************************************************************************************** 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
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
import cz.zcu.kiv.formgen.annotation.MultiForm;
import cz.zcu.kiv.formgen.annotation.PreviewLevel;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.model.FormItem;
import cz.zcu.kiv.formgen.model.Type;
import cz.zcu.kiv.formgen.model.constraints.Cardinality;
import cz.zcu.kiv.formgen.model.constraints.DefaultValue;
import cz.zcu.kiv.formgen.model.constraints.Length;
import cz.zcu.kiv.formgen.model.constraints.NumericalValue;
import cz.zcu.kiv.formgen.model.constraints.PossibleValues;


/**
 * Parses class objects using reflection and creates corresponding {@link Form} model.
 * 
 * @author Jakub Krauz
 */
public class ClassParser {

    /** Logger. */
    final Logger logger = LoggerFactory.getLogger(ClassParser.class);

    /** Type mapper object. */
    private final TypeMapper mapper = new TypeMapper();


    /**
     * Parses the given class and creates a new {@link Form} object.
     * 
     * @param cls The class object to be parsed.
     * @return The newly created {@link Form} object.
     */
    public Form parse(Class<?> cls) {
        Form form = _parse(cls, cls.getSimpleName(), 0);
        form.setLayoutName(form.getName() + "-generated");
        return form;
    }


    /**
     * Parses the given class and adds the corresponding {@link Form} object
     * as a subform to the existing model (used for multiple-class forms).
     * 
     * @param cls The class object to be parsed.
     * @param form The model to which the parsed class will be added.
     */
    public void parse(Class<?> cls, Form form) {
        form.addItem(_parse(cls, cls.getSimpleName(), form.highestItemId() + 1));
    }


    /**
     * Creates a multi-form object defined by the given annotation.
     * 
     * @param annotation The {@link MultiForm MultiForm} annotation.
     * @return New form defined by the annotation.
     */
    public Form createMultiform(MultiForm annotation) {
        Form form = new Form(annotation.value());
        form.setId(0);
        form.setLayoutName(annotation.value() + "-generated");
        if (!annotation.label().isEmpty())
            form.setLabel(annotation.label());
        return form;
    }


    /**
     * Recursively parses the given class object and creates a new {@link Form} model.
     * 
     * <p>
     * All fields annotated with the {@link cz.zcu.kiv.formgen.annotation.FormItem @FormItem}
     * annotation are added to the form as its items. Referenced objects are parsed the same
     * way and corresponding {@link Form} objects are added as subforms.
     * </p>
     * 
     * @param cls The class object to be parsed.
     * @param formName Name of the form to be created.
     * @param id ID of the form.
     * @return The newly created {@link Form}.
     */
    private Form _parse(Class<?> cls, String formName, int id) {
        Form form = createForm(formName, cls);
        form.setId(id++);

        final Class<cz.zcu.kiv.formgen.annotation.FormItem> formItemClass = 
                cz.zcu.kiv.formgen.annotation.FormItem.class;

        // iterate over all fields annotated with @FormItem
        for (Field f : ReflectionUtils.annotatedFields(cls, formItemClass)) {
            if (mapper.isSimpleType(f.getType())) {
                FormField item = createFormField(f, id++);
                form.addItem((FormItem) item);
                if (isPreviewField(f))
                    setPreviewField(form, item, f);
            } else if (Collection.class.isAssignableFrom(f.getType())) {
                FormItem set = createFormSet(f, id++);
                form.addItem(set);
                id = set.getId() + 1;
            } else {
                Form subform = _parse(f.getType(), f.getName(), id++);
                cz.zcu.kiv.formgen.annotation.FormItem annotation = 
                        f.getAnnotation(formItemClass);
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
     * Creates a new {@link Form} representing the given class.
     * 
     * @param name Name of the form.
     * @param cls The class object.
     * @return The newly created {@link Form}.
     */
    private Form createForm(String name, Class<?> cls) {
        Form form = new Form(name);
        form.setDataReference(cls.getSimpleName());
        form.setCardinality(Cardinality.SINGLE_VALUE);

        // determine label of the form
        String label = guessLabel(cls.getSimpleName());
        if (cls.isAnnotationPresent(cz.zcu.kiv.formgen.annotation.Form.class)) {
            cz.zcu.kiv.formgen.annotation.Form annot = cls
                    .getAnnotation(cz.zcu.kiv.formgen.annotation.Form.class);
            if (!annot.label().isEmpty())
                label = annot.label();
        }
        form.setLabel(label);

        // description
        if (cls.isAnnotationPresent(FormDescription.class)) {
            FormDescription description = cls.getAnnotation(FormDescription.class);
            form.setDescription(description.value());
        }

        return form;
    }


    /**
     * Creates a new {@link FormField} representing the given class field.
     * 
     * @param field The class field.
     * @param id ID of the {@link FormField} to be created.
     * @return The newly created form field.
     */
    private FormField createFormField(Field field, int id) {
        Type type = mapper.mapType(field.getType());
        FormField formField;
        if (type == Type.CHECKBOX) {
            formField = new FormField(field.getName());
            formField.setType(type);
        } else {
            formField = new FormField(field.getName(), type, mapper.mapDatatype(field.getType()));
            formField.setCardinality(Cardinality.SINGLE_VALUE);
        }
        formField.setId(id);

        cz.zcu.kiv.formgen.annotation.FormItem formItemAnnot = field
                .getAnnotation(cz.zcu.kiv.formgen.annotation.FormItem.class);
        String label = formItemAnnot.label();
        formField.setLabel(label.isEmpty() ? guessLabel(field.getName()) : label);
        formField.setRequired(formItemAnnot.required());

        if (field.isAnnotationPresent(FormItemRestriction.class)) {
            FormItemRestriction restriction = field.getAnnotation(FormItemRestriction.class);
            if (restriction.minLength() != -1)
                formField.addConstraint(Length.MIN(restriction.minLength()));
            if (restriction.maxLength() != -1)
                formField.addConstraint(Length.MAX(restriction.maxLength()));
            ;
            if (!Double.isNaN(restriction.minValue())) {
                if (TypeMapper.isIntegerType(field.getType()))
                    formField.addConstraint(NumericalValue.MIN((int) restriction.minValue()));
                else
                    formField.addConstraint(NumericalValue.MIN(restriction.minValue()));
            }
            if (!Double.isNaN(restriction.maxValue())) {
                if (TypeMapper.isIntegerType(field.getType()))
                    formField.addConstraint(NumericalValue.MAX((int) restriction.maxValue()));
                else
                    formField.addConstraint(NumericalValue.MAX(restriction.maxValue()));
            }
            if (!restriction.defaultValue().isEmpty())
                formField.addConstraint(new DefaultValue(restriction.defaultValue()));
            if (restriction.values().length > 1)
                formField.addConstraint(new PossibleValues(restriction.values()));
        }

        return formField;
    }


    /**
     * Creates a new {@link FormItem} representing the given field which
     * must be a collection.
     * 
     * <p>
     * Note that the collection should be parameterized so as the method
     * can determine items of the collection. Otherwise null is returned.
     * </p>
     * 
     * @param field The class field (of Collection type).
     * @param id ID of the form item.
     * @return The newly created form item, or null if the collection is not parameterized.
     */
    private FormItem createFormSet(Field field, int id) {

        // check whether the collection type is parameterized
        if (!(field.getGenericType() instanceof ParameterizedType)) {
            logger.warn("Can not parse non-parameterized collection \"{}\"", field.getName());
            return null;
        }

        Class<?> clazz = ReflectionUtils.genericParameter((ParameterizedType) field.getGenericType());
        FormItem item;

        // parse the content type
        if (mapper.isSimpleType(clazz)) {
            item = new FormField(field.getName(), mapper.mapType(clazz), mapper.mapDatatype(clazz));
            item.setId(id);
        } else {
            item = _parse(clazz, field.getName(), id);
        }

        cz.zcu.kiv.formgen.annotation.FormItem formItemAnnot = field
                .getAnnotation(cz.zcu.kiv.formgen.annotation.FormItem.class);
        String label = formItemAnnot.label();
        item.setLabel(label.isEmpty() ? field.getName() : label);
        item.setRequired(formItemAnnot.required());
        item.setCardinality(Cardinality.UNRESTRICTED);

        return item;
    }


    /**
     * Creates a user-friendly string from a camelCase notation.
     * 
     * <p>
     * The returned string starts with an uppercase letter and individual words
     * are separated by spaces.
     * For example: "camelCaseName" is converted to "Camel case name". 
     * </p>
     * 
     * @param name The name in camelCase.
     * @return User-friendly string.
     */
    private String guessLabel(String name) {
        // first letter must be lowercase
        if (Character.isUpperCase(name.charAt(0)))
            name = Character.toLowerCase(name.charAt(0)) + name.substring(1);

        // split the name in camelCase notation to individual words
        String[] words = name.split("(?=\\p{Upper})");

        // build the label from individual words separated by space
        StringBuilder buf = new StringBuilder(name.length() + words.length - 1);
        buf.append(Character.toUpperCase(words[0].charAt(0)));  // first letter in upper case
        buf.append(words[0].substring(1));  // rest of the first word
        for (int i = 1; i < words.length; i++) {
            buf.append(' ');
            buf.append(words[i].toLowerCase());
        }

        return buf.toString();
    }
    
    
    /**
     * Decides whether the given field is a preview field.
     * 
     * @param field The field.
     * @return True if the field is a preview field (either {@link PreviewLevel#MAJOR MAJOR}
     * or {@link PreviewLevel#MINOR MINOR}), false otherwise.
     */
    private boolean isPreviewField(Field field) {
        cz.zcu.kiv.formgen.annotation.FormItem annot = 
                field.getAnnotation(cz.zcu.kiv.formgen.annotation.FormItem.class);
        if (annot != null) {
            return annot.preview() != PreviewLevel.NONE;
        }
        return false;
    }
    
    
    /**
     * Sets the given <code>formField</code> as the preview field of the given <code>form</code>.
     * The <code>formField</code> represents the original class <code>field</code>.
     * 
     * @param form The form.
     * @param formField The form field.
     * @param field The original class field.
     */
    private void setPreviewField(Form form, FormField formField, Field field) {
        cz.zcu.kiv.formgen.annotation.FormItem annot = 
                field.getAnnotation(cz.zcu.kiv.formgen.annotation.FormItem.class);
        if (annot != null) {
            if (annot.preview() == PreviewLevel.MAJOR)
                form.setMajorPreviewField(formField);
            else if (annot.preview() == PreviewLevel.MINOR)
                form.setMinorPreviewField(formField);
        }
    }


}
