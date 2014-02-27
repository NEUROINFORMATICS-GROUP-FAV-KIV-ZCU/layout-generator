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
 * AbstractTypeMapper.java, 12. 2. 2014 10:16:18 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;


/**
 *
 * @author Jakub Krauz
 */
public abstract class AbstractTypeMapper implements TypeMapper {

    protected static final BidiMap WRAPPER_TYPES = new DualHashBidiMap();

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

}
