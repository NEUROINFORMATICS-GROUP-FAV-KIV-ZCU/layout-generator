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
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.Type;


/**
 * Provides mapping between Java types and types used in the {@link Form} model.
 *
 * @author Jakub Krauz
 */
public class TypeMapper {
    
    /** Bi-directional map of primitive types and their wrapper types. */
    private static final BidiMap WRAPPER_TYPES = new DualHashBidiMap();

    /** Map of Java types and their mappings to form item type. */
    private static final Map<Class<?>, Type> TYPES = new HashMap<Class<?>, Type>();
    
    /** Map of Java types and their mappings to textbox datatypes. */
    private static final Map<Class<?>, FieldDatatype> DATATYPES = new HashMap<Class<?>, FieldDatatype>();
  
    
    // fill the WRAPPER_TYPES map
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
    
    
    /**
     * Determines whether the given class is a wrapper type.
     * 
     * @param cls The tested class.
     * @return True if <code>cls</code> is a wrapper type, false otherwise.
     */
    public static boolean isWrapperType(Class<?> cls) {
        return WRAPPER_TYPES.containsKey(cls);
    }
    
    
    /**
     * Converts a wrapper type to a corresponding primitive type.
     * 
     * @param wrapperType The wrapper type to be converted.
     * @return The corresponding primitive type.
     */
    public static Class<?> toPrimitiveType(Class<?> wrapperType) {
        if (wrapperType.isPrimitive())
            return wrapperType;  // not a wrapper type
        else
            return (Class<?>) WRAPPER_TYPES.get(wrapperType);
    }
    
    
    /**
     * Converts a primitive type to a corresponding wrapper type.
     * 
     * @param primitiveType The primitive type to be converted.
     * @return The corresponding wrapper type.
     */
    public static Class<?> toWrapperType(Class<?> primitiveType) {
        if (primitiveType.isPrimitive())
            return (Class<?>) WRAPPER_TYPES.getKey(primitiveType);
        else
            return primitiveType;  // not a primitive type
    }
    
    
    /**
     * Determines whether the given type is a whole-number type
     * (i.e. byte, short, int, long or their object wrappers).
     * 
     * @param type The tested type.
     * @return True if the type is a whole-number type, false otherwise.
     */
    public static boolean isIntegerType(Class<?> type) {
        Class<?> cls = type.isPrimitive() ? type : toPrimitiveType(type);
        return (cls == byte.class || cls == short.class || cls == int.class || cls == long.class);
    }
    
    
    /**
     * Determines, whether the given type is a number type, i.e. either a whole-number
     * type (see {@link #isIntegerType(Class)}, or a floating-point number type
     * (float, double or their object wrappers).
     * 
     * @param type The tested type.
     * @return True if the type is number, false otherwise.
     */
    public static boolean isNumberType(Class<?> type) {
        return Number.class.isAssignableFrom(toWrapperType(type));
    }

    
    // fill the TYPES map
    static {
        TYPES.put(Boolean.TYPE, Type.CHECKBOX);
        TYPES.put(Byte.TYPE, Type.TEXTBOX);
        TYPES.put(Character.TYPE, Type.TEXTBOX);
        TYPES.put(Short.TYPE, Type.TEXTBOX);
        TYPES.put(Integer.TYPE, Type.TEXTBOX);
        TYPES.put(Long.TYPE, Type.TEXTBOX);
        TYPES.put(Float.TYPE, Type.TEXTBOX);
        TYPES.put(Double.TYPE, Type.TEXTBOX);
        TYPES.put(String.class, Type.TEXTBOX);
        TYPES.put(java.util.Date.class, Type.TEXTBOX);
        TYPES.put(java.sql.Date.class, Type.TEXTBOX);
    }
    
    
    // fill the DATATYPES map
    static {
        DATATYPES.put(Boolean.TYPE, FieldDatatype.BOOLEAN);
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

    

    /**
     * Determines whether the given type is considered a simple type in the terms
     * of form layout.
     * 
     * @param type The tested type.
     * @return True if the given type is considered simple, false otherwise.
     */
    public boolean isSimpleType(Class<?> type) {        
        return type.isPrimitive() || isWrapperType(type) || TYPES.containsKey(type) || TYPES.containsKey(type.getSuperclass());
    }



    /**
     * Maps the given type to an appropriate form item type.
     * 
     * @param type The type being mapped.
     * @return Type of a form item.
     */
    public Type mapType(Class<?> type) {
        if (isWrapperType(type))
            type = toPrimitiveType(type);
        
        if (TYPES.containsKey(type))
            return TYPES.get(type);
        
        for (Class<?> cls : TYPES.keySet()) {
            if (cls.isAssignableFrom(type))
                return TYPES.get(cls);
        }
            
        // default
        return Type.FORM;
    }
    
    
    /**
     * Maps the given type to a form field datatype.
     * 
     * @param type The type being mapped.
     * @return Datatype of form field.
     */
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
