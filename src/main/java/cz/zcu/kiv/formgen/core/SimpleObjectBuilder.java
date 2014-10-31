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
 * SimpleObjectBuilder.java, 1. 3. 2014 10:18:45 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.zcu.kiv.formgen.ObjectBuilder;
import cz.zcu.kiv.formgen.ObjectBuilderException;
import cz.zcu.kiv.formgen.model.FormData;
import cz.zcu.kiv.formgen.model.FormDataField;
import cz.zcu.kiv.formgen.model.FormDataItem;


/**
 * Builds original data objects (POJOs) from {@link FormData} model.
 * 
 * <p>
 * This builder instantiates all data objects itself according to the structure
 * of the passed {@link FormData} model. If you need support for persistent objects
 * referenced by IDs, see {@link PersistentObjectBuilder}.
 * </p>
 * 
 * @author Jakub Krauz
 */
public class SimpleObjectBuilder implements ObjectBuilder {
    
    /** Logger. */
    private final Logger logger = LoggerFactory.getLogger(SimpleObjectBuilder.class);
    
    
    /**
     * {@inheritDoc}
     */
    public Object build(FormData formData, Class<?> type) throws ObjectBuilderException {
        return createInstance(type, formData);
    }
    
    
    /**
     * {@inheritDoc}
     */
    public <T> T buildTyped(FormData formData, Class<T> type) throws ObjectBuilderException {
        try {
            return type.cast(createInstance(type, formData));
        } catch (ClassCastException e) {
            throw new ObjectBuilderException("Build error.", e);
        }
    }
    
    
    /**
     * Fills the given object <code>obj</code> with values form <code>formData</code>.
     * 
     * @param obj The object to be filled with data.
     * @param formData The model object representing the data.
     * @throws NoSuchFieldException If the object being filled does not contain a field defined in {@link FormData}.
     * @throws IllegalAccessException If the access to some of the object's field was refused.
     * @throws ObjectBuilderException If another error occured.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void fill(Object obj, FormData formData) throws NoSuchFieldException,
                                                              IllegalAccessException,  
                                                              ObjectBuilderException {
        
        for (FormDataItem item : formData.getItems()) {
            
            Field field = obj.getClass().getDeclaredField(item.getName());
            
            // check accessibility of the field
            if (!Modifier.isPublic(field.getModifiers()))
                field.setAccessible(true);
            
            if (item instanceof FormDataField) {
                Object value = ((FormDataField) item).getValue();
                if (value == null)
                	continue;
                
                // convert to appropriate number type if needed
                if (TypeMapper.isNumberType(field.getType()))
                    value = toNumber(value, field.getType());
                
                // get character value if needed
                if (field.getType() == Character.TYPE)
                	value = value.toString().charAt(0);
                
                // convert date/time values to appropriate type if needed
                if (TypeMapper.isDateOrTimeType(field.getType()))
                	value = toProperDateType(value, field.getType());

                field.set(obj, value);
                
            } else if (item instanceof FormData) {
                
                FormData data = (FormData) item;
                
                if (FormData.SET.equals(data.getType())) {
                    // create collection
                    if (!(field.getGenericType() instanceof ParameterizedType))
                        throw new ObjectBuilderException("Cannot create a non-parameterized collection.");
                    Class<?> innerType = ReflectionUtils.genericParameter((ParameterizedType) field.getGenericType());
                    if (innerType == null)
                        throw new ObjectBuilderException("Cannot create collection.");
                    Collection collection = (Collection) field.get(obj);
                    if (collection == null) {
                        collection = instantiateCollection((Class<? extends Collection>) field.getType());
                        field.set(obj, collection);
                    }
                    
                    if (data.getItems().iterator().next() instanceof FormData) {
                        for (FormDataItem inner : data.getItems())
                            collection.add(createInstance(innerType, (FormData) inner));
                    } else {
                        for (FormDataItem inner : data.getItems()) {
                            Object value = ((FormDataField) inner).getValue();
                            // convert to appropriate number type
                            if (TypeMapper.isNumberType(innerType))
                                value = toNumber(value, innerType);
                            collection.add(value);
                        }
                    }
                } else {
                    field.set(obj, createInstance(field.getType(), (FormData) item));
                }
                
                
            }
            
        }  // end for

    }
    
    
    // TODO add support for various date/time types conversion
    protected Object toProperDateType(Object obj, Class<?> type) throws ObjectBuilderException {
        if (obj == null)
        	return null;
        
    	if (Date.class.equals(type)) {
        	if (obj instanceof Date)
        		return obj;
        	else if (obj instanceof LocalDate)
            	return ((LocalDate) obj).toDate();
        }
        
        else if (java.sql.Date.class.equals(type)) {
        	if (obj instanceof java.sql.Date)
        		return obj;
        	else if (obj instanceof Date)
        		return new java.sql.Date(((Date) obj).getTime());
        	else if (obj instanceof LocalDate)
        		return new java.sql.Date(((LocalDate) obj).toDate().getTime());
        }
        
        else if (java.sql.Time.class.equals(type)) {
        	if (obj instanceof java.sql.Time)
        		return obj;
        	else if (obj instanceof LocalTime)
        		return java.sql.Time.valueOf(((LocalTime) obj).toString());
        }
        
        else if (java.sql.Timestamp.class.equals(type)) {
        	if (obj instanceof java.sql.Timestamp)
        		return obj;
        	else if (obj instanceof Date)
        		return new java.sql.Timestamp(((Date) obj).getTime());
        	else if (obj instanceof LocalDate)
            	return new java.sql.Timestamp(((LocalDate) obj).toDate().getTime());
        }
        
        else if (LocalDate.class.equals(type)) {
        	if (obj instanceof LocalDate)
        		return obj;
        	else if (obj instanceof Date)
        		return new LocalDate(obj);
        }
        
        else if (LocalTime.class.equals(type)) {
        	if (obj instanceof LocalTime)
        		return obj;
        }
        
        else if (LocalDateTime.class.equals(type)) {
        	if (obj instanceof LocalDateTime)
        		return obj;
        	else if (obj instanceof Date)
        		return new LocalDateTime(obj);
        }
        
        throw new ObjectBuilderException("Unsupported date/time conversion from " 
        				+ obj.getClass().getName() + " to " + type.getName());
	}
    
    
    /**
     * Converts the given object <code>obj</code> to an instance of java.lang.Number.
     * The concrete number class is determined by the <code>numberType</code> argument.
     * 
     * @param obj The object to be converted to number.
     * @param numberType The required type of the result (descendant of java.lang.Number).
     * @return The value of required number type.
     * @throws NumberFormatException If the number cannot be parsed from the passed object.
     */
    protected Number toNumber(Object obj, Class<?> numberType) throws NumberFormatException {
        Number value = null;
        Class<?> type = TypeMapper.toPrimitiveType(numberType);
        
        if (obj instanceof Number) {
            if (type.equals(Byte.TYPE))
                value = ((Number) obj).byteValue();
            else if (type.equals(Short.TYPE))
                value = ((Number) obj).shortValue();
            else if (type.equals(Integer.TYPE))
                value = ((Number) obj).intValue();
            else if (type.equals(Long.TYPE))
                value = ((Number) obj).longValue();
            else if (type.equals(Float.TYPE))
                value = ((Number) obj).floatValue();
            else if (type.equals(Double.TYPE))
                value = ((Number) obj).doubleValue();
        } else {
            // try to parse the value from string
            if (type.equals(Byte.TYPE))
                value = Byte.parseByte(obj.toString());
            else if (type.equals(Short.TYPE))
                value = Short.parseShort(obj.toString());
            else if (type.equals(Integer.TYPE))
                value = Integer.parseInt(obj.toString());
            else if (type.equals(Long.TYPE))
                value = Long.parseLong(obj.toString());
            else if (type.equals(Float.TYPE))
                value = Float.parseFloat(obj.toString());
            else if (type.equals(Double.TYPE))
                value = Double.parseDouble(obj.toString());
        }
        
        return value;
    }
    
    
    /**
     * Instantiates a new collection.
     * 
     * @param type Required type of the collection (e.g. Set or List).
     * @return The newly created collection.
     * @throws ObjectBuilderException If the collection type is not supported.
     */
    @SuppressWarnings("rawtypes")
    protected Collection<?> instantiateCollection(Class<? extends Collection> type) throws ObjectBuilderException {
        if (type.equals(Set.class))
            return new HashSet();
        if (type.equals(List.class))
            return new ArrayList();
        
        throw new ObjectBuilderException("Unsupported collection type.");
    }
    
    
    /**
     * Creates a new instance of the given type and fills it with data from the passed {@link FormData} object.
     * 
     * @param type The type to be instantiated.
     * @param data The data model.
     * @return The newly instantiated object.
     * @throws ObjectBuilderException If the instance cannot be created.
     */
    protected Object createInstance(Class<?> type, FormData data) throws ObjectBuilderException {
        Object instance = null;
        
        try {
            instance = type.newInstance();
            fill(instance, data);
        } catch (Exception e) {
            final String message = "Cannot create instance of " + ((type == null) ? "null" : type.getName());
            logger.error(message, e);
            throw new ObjectBuilderException(message, e);
        }
        
        return instance;
    }

    
}
