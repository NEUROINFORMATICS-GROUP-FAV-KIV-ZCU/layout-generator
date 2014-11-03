/***********************************************************************************************************************
 *
 * This file is part of the layout-generator project
 *
 * =================================================
 *
 * Copyright (C) 2014 by University of West Bohemia (http://www.zcu.cz/en/)
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
 * DataParser.java, 2. 4. 2014 11:36:46 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zcu.kiv.formgen.annotation.FormId;
import cz.zcu.kiv.formgen.annotation.FormItem;
import cz.zcu.kiv.formgen.model.FormData;
import cz.zcu.kiv.formgen.model.FormDataField;
import cz.zcu.kiv.formgen.model.FormDataItem;


/**
 * Parses data objects using reflection and creates a corresponding {@link FormData} model.
 * 
 * @author Jakub Krauz
 */
public class DataParser {
    
    /** Type mapper object. */
    private final TypeMapper mapper = new TypeMapper();
    
    /** Logger. */
    final Logger logger = LoggerFactory.getLogger(DataParser.class);
    
    /** Parse referenced objects as well? */
    private boolean includeReferences;
    
    
    /**
     * Parses the given data object and creates a new {@link FormData} model.
     * 
     * @param obj Data object to be parsed.
     * @param includeReferences If true, the parser will parse referenced objects as well. Otherwise
     *                           only their IDs are added.
     * @return The newly created FormData object.
     */
    public FormData parse(Object obj, boolean includeReferences) {
        this.includeReferences = includeReferences;
        FormData data = _parse(obj, obj.getClass().getSimpleName() + "_" + getId(obj).toString());
        data.setLabel(data.getName());
        return data;
    }
    
    
    /**
     * Recursively parses the data object and creates a corresponding {@link FormData} model.
     * 
     * @param obj The data object to be parsed.
     * @param name Name of the form data item.
     * @return The newly created form data model.
     */
    private FormData _parse(Object obj, String name) {
        if (obj == null)
            return null;
        
        FormData form = new FormData(obj.getClass().getSimpleName(), name);
        
        Field idField = ReflectionUtils.annotatedField(obj.getClass(), FormId.class);
        if (idField != null)
            form.setId(ReflectionUtils.value(idField, obj));
        
        for (Field f : ReflectionUtils.annotatedFields(obj.getClass(), cz.zcu.kiv.formgen.annotation.FormItem.class)) {
            Object value = ReflectionUtils.value(f, obj);
            if (value == null)
            	continue;
        	
        	FormDataItem item;
        	if (mapper.isSimpleType(f.getType())) {
                item = createDataField(f, value);
            } else if (Collection.class.isAssignableFrom(f.getType())) {
                item = createDataSet(f, value);
            } else if (!includeReferences) {
                item = createDataReference(f, value);
            } else {
                item = _parse(value, f.getName());
            }
            
            // label
        	if (item != null) {
	            FormItem formItemAnnot = f.getAnnotation(FormItem.class);
	            String label = formItemAnnot.label();
	            item.setLabel(label.isEmpty() ? StringUtils.beautifyString(f.getName()) : label);
        	}
            
            form.addItem(item);
        }
        
        return form;
    }
    

    /**
     * Creates a data set, i.e. data item with multiple records of the same type.
     * 
     * @param field The field representing the set.
     * @param value The referenced data object.
     * @return The newly created data set.
     */
    private FormDataItem createDataSet(Field field, Object value) {
        String name = field.getName();
        
        Collection<?> collection = (Collection<?>) value;
        if (collection == null || collection.isEmpty())
            return null;
        
        FormData dataSet = new FormData("set", field.getName());

        int index = 0;
        Class<?> type = ReflectionUtils.genericParameter((ParameterizedType) field.getGenericType());
        for (Object o : collection) {
            if (mapper.isSimpleType(o.getClass())) {
                FormDataField formField = new FormDataField(name + "[" + index++ + "]");
                formField.setValue(o);
                dataSet.addItem(formField);
            } else if (!includeReferences) {
                FormData reference = new FormData(type.getSimpleName(), name + "[" + index++ + "]");
                reference.setId(getId(o));
                dataSet.addItem(reference);
            } else {
                FormData form = _parse(o, name + "[" + index++ + "]");
                dataSet.addItem(form);   
            }
        }
        
        return dataSet;
    }


    /**
     * Creates a new data field representing the given class <code>field</code>.
     * 
     * @param field The class field.
     * @param value The referenced data object.
     * @return The newly created data field.
     */
    private FormDataField createDataField(Field field, Object value) {
        FormDataField dataField = new FormDataField(field.getName());
        dataField.setValue(value);
        return dataField;
    }
    
    
    /**
     * Creates a new data reference, i.e. a form data item with the only ID property set.
     * 
     * @param field The class field.
     * @param value The referenced data object.
     * @return The newly created data reference.
     */
    private FormData createDataReference(Field field, Object value) {
        if (value != null) {
            FormData reference = new FormData(field.getType().getSimpleName(), field.getName());
            reference.setId(getId(value));
            return reference;
        } else {
            logger.warn("Cannot create reference to a null value!");
            return null;
        }
    }
    
    
    /**
     * Returns ID of the given data object.
     *  
     * @param entity The data object.
     * @return ID of the data object.
     */
    private Object getId(Object entity) {
        Field idField = ReflectionUtils.annotatedField(entity.getClass(), FormId.class);
        return ReflectionUtils.value(idField, entity);
    }
   

}
