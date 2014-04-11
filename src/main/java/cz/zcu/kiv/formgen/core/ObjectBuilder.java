/***********************************************************************************************************************
 *
 * This file is part of the layout-generator project
 *
 * ==========================================
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
 * ObjectBuilder.java, 1. 3. 2014 10:18:45 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cz.zcu.kiv.formgen.model.FormData;
import cz.zcu.kiv.formgen.model.FormDataField;
import cz.zcu.kiv.formgen.model.FormDataItem;


/**
 *
 * @author Jakub Krauz
 */
public class ObjectBuilder {
    
    /** Logger. */
    final Logger logger = LoggerFactory.getLogger(ObjectBuilder.class);
    
    
    public <T> T build(FormData formData, Class<T> type) throws ObjectBuilderException {
        T obj = null;
        
        try {
            obj = type.newInstance();
            fill(obj, formData);
        } catch (Exception e) {
            logger.error("Can not create instance of " + type.getCanonicalName(), e);
            throw new ObjectBuilderException("Can not create instance of " + type.getCanonicalName(), e);
        }
        
        return obj;
    }
    
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void fill(Object obj, FormData formData) throws SecurityException, NoSuchFieldException, 
                        IllegalArgumentException, IllegalAccessException, InstantiationException, ObjectBuilderException {
        
        for (FormDataItem item : formData.getItems()) {
            
            Field field = obj.getClass().getDeclaredField(item.getName());
            field.setAccessible(true);
            
            if (item instanceof FormDataField) {
                Object value = ((FormDataField) item).getValue();
                
                // convert to appropriate number type
                if (TypeMapper.isNumberType(field.getType()))
                    value = toNumber(value, field.getType());
                
                field.set(obj, value);
                
            } else if (item instanceof FormData) {
                
                FormData data = (FormData) item;
                
                if (FormData.SET.equals(data.getType())) {
                    if (!(field.getGenericType() instanceof ParameterizedType))
                        throw new ObjectBuilderException("Cannot create a non-parameterized collection.");
                    Class<?> innerType = Utils.genericParameter((ParameterizedType) field.getGenericType());
                    if (innerType == null)
                        throw new ObjectBuilderException("Cannot create collection.");
                    Collection collection = (Collection) field.get(obj);
                    if (collection == null) {
                        collection = instantiateCollection((Class<? extends Collection>) field.getType());
                        field.set(obj, collection);
                    }
                    
                    if (data.getItems().iterator().next() instanceof FormData) {
                        for (FormDataItem inner : data.getItems()) {
                            Object o = innerType.newInstance();
                            fill(o, (FormData) inner);
                            collection.add(o);
                        }
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
                    Object o = field.getType().newInstance();
                    fill(o, (FormData) item);
                    field.set(obj, o);
                }
                
                
            }
            
        }  // end for

    }
    
    
    
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
    
    
    
    @SuppressWarnings("rawtypes")
    protected Collection<?> instantiateCollection(Class<? extends Collection> type) {
        if (type.equals(Set.class))
            return new HashSet();
        if (type.equals(List.class))
            return new ArrayList();
        
        return null;
    }

    
}
