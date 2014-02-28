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
 * TypeMapper.java, 16. 12. 2013 13:37:27 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import cz.zcu.kiv.formgen.model.FieldDatatype;
import cz.zcu.kiv.formgen.model.FieldType;


/**
 * This class is used to map types from Java to those used in form layouts.
 *
 * @author Jakub Krauz
 */
public class TypeMapper {
        
    private static final BidiMap WRAPPER_TYPES = new DualHashBidiMap();

    private static final Map<Class<?>, FieldType> TYPES = new HashMap<Class<?>, FieldType>();
    
    private static final Map<Class<?>, FieldDatatype> DATATYPES = new HashMap<Class<?>, FieldDatatype>();
    
    private static TypeMapper instance;
  

    static {
        WRAPPER_TYPES.put(Boolean.class, Boolean.TYPE);
        WRAPPER_TYPES.put(Byte.class, Byte.TYPE);
        WRAPPER_TYPES.put(Character.class, Character.TYPE);
        WRAPPER_TYPES.put(Short.class, Short.TYPE);
        WRAPPER_TYPES.put(Integer.class, Integer.TYPE);
        WRAPPER_TYPES.put(Long.class, Long.TYPE);
        WRAPPER_TYPES.put(Float.class, Float.TYPE);
        WRAPPER_TYPES.put(Double.class, Double.TYPE);
    }
    
    
    public static boolean isWrapperType(Class<?> cls) {
        return WRAPPER_TYPES.containsKey(cls);
    }
    
    
    public static Class<?> toPrimitiveType(Class<?> wrapperType) {
        return (Class<?>) WRAPPER_TYPES.get(wrapperType);
    }
    
    
    public static Class<?> toWrapperType(Class<?> primitiveType) {
        return (Class<?>) WRAPPER_TYPES.getKey(primitiveType);
    }

    
    
    static {
        TYPES.put(Boolean.TYPE, FieldType.CHECKBOX);
        TYPES.put(Byte.TYPE, FieldType.TEXTBOX);
        TYPES.put(Character.TYPE, FieldType.TEXTBOX);
        TYPES.put(Short.TYPE, FieldType.TEXTBOX);
        TYPES.put(Integer.TYPE, FieldType.TEXTBOX);
        TYPES.put(Long.TYPE, FieldType.TEXTBOX);
        TYPES.put(Float.TYPE, FieldType.TEXTBOX);
        TYPES.put(Double.TYPE, FieldType.TEXTBOX);
        TYPES.put(String.class, FieldType.TEXTBOX);
        TYPES.put(java.util.Date.class, FieldType.TEXTBOX);
        TYPES.put(java.sql.Date.class, FieldType.TEXTBOX);
        TYPES.put(java.util.Collection.class, FieldType.SET);
    }
    
    
    static {
        DATATYPES.put(Byte.TYPE, FieldDatatype.INTEGER);
        DATATYPES.put(Character.TYPE, FieldDatatype.STRING);
        DATATYPES.put(Short.TYPE, FieldDatatype.INTEGER);
        DATATYPES.put(Integer.TYPE, FieldDatatype.INTEGER);
        DATATYPES.put(Long.TYPE, FieldDatatype.INTEGER);
        DATATYPES.put(Float.TYPE, FieldDatatype.NUMBER);
        DATATYPES.put(Double.TYPE, FieldDatatype.NUMBER);
        DATATYPES.put(String.class, FieldDatatype.STRING);
        DATATYPES.put(java.util.Date.class, FieldDatatype.DATE);
        DATATYPES.put(java.sql.Date.class, FieldDatatype.DATE);
    }
    
    
    public static TypeMapper instance() {
        if (instance == null)
            instance = new TypeMapper();
        return instance;
    }
    
    
    private TypeMapper() {
        // private ctor
    }

    

    /**
     * Determines whether given Java type is considered a simple type in the terms
     * of form layout.
     * 
     * @param type the Java type
     * @return true if the given type is considered simple, false otherwise
     */
    public boolean isSimpleType(Class<?> type) {        
        return type.isPrimitive() || isWrapperType(type) || TYPES.containsKey(type) || TYPES.containsKey(type.getSuperclass());
    }



    /**
     * Maps the given Java type to an appropriate type used in form layouts.
     * @param type the Java type
     * @return type name used in form layout
     */
    public FieldType mapType(Class<?> type) {
        if (isWrapperType(type))
            type = toPrimitiveType(type);
        
        if (TYPES.containsKey(type))
            return TYPES.get(type);
        
        for (Class<?> cls : TYPES.keySet()) {
            if (cls.isAssignableFrom(type))
                return TYPES.get(cls);
        }
            
        // default
        return FieldType.TEXTBOX;
    }
    
    
    
    public FieldDatatype mapDatatype(Class<?> type) {
        if (isWrapperType(type))
            type = toPrimitiveType(type);
        
        if (DATATYPES.containsKey(type))
            return DATATYPES.get(type);

        // check if the type is a subclass of mapped types
        for (Class<?> cls : DATATYPES.keySet()) {
            if (cls.isAssignableFrom(type))
                return DATATYPES.get(cls);
        }
        
        // default
        return FieldDatatype.STRING;
    }


}
