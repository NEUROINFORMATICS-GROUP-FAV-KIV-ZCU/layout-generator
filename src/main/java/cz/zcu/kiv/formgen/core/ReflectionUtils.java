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
 * ReflectionUtils.java, 3. 3. 2014 18:14:28 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Jakub Krauz
 */
public class ReflectionUtils {
    
    static final Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);
    
    
    public static Class<?> genericParameter(ParameterizedType type) {
        Type[] parameters = type.getActualTypeArguments();
        if (parameters.length == 1 && parameters[0] instanceof Class) {
            return (Class<?>) parameters[0];
        } else {
            logger.warn("The parameterized type does not have exactly one type parameter.");
            return null;
        }
    }
    
    
    /**
     * Extracts the given annotation from the object.
     * 
     * @param object the object
     * @param annotationClass class of the annotation to be extracted
     * @return extracted annotation or null
     */
    public static <A extends Annotation> A annotation(Object object, Class<A> annotationClass) {
        return object.getClass().getAnnotation(annotationClass);
    }
    
    
    public static Collection<Field> allFields(Class<?> cls) {
        Collection<Field> collection = new HashSet<Field>();
        
        while (cls != null) {
            for (Field f : cls.getDeclaredFields())
                collection.add(f);
            cls = cls.getSuperclass();
        }
        
        return collection;
    }
    
    
    /**
     * Returns the first field of the class cls marked with the given annotation or null.
     * 
     * @param cls - the class
     * @param annotationClass - the class of the annotation
     * @return an annotated field or null
     */
    public static Field annotatedField(Class<?> cls, Class<? extends Annotation> annotationClass) {
        for (Field f : allFields(cls)) {
            if (f.isAnnotationPresent(annotationClass))
                return f;
        }
        return null;
    }
    
    
    /**
     * Returns collection containing fields of class cls marked with the given annotation.
     * 
     * @param cls - the class
     * @param annotationClass - the class of the annotation
     * @return collection of annotated fields
     */
    public static Collection<Field> annotatedFields(Class<?> cls, Class<? extends Annotation> annotationClass) {
        Collection<Field> collection = new HashSet<Field>();
        
        for (Field f : allFields(cls)) {
            if (f.isAnnotationPresent(annotationClass))
                collection.add(f);
        }
        
        return collection;
    }
    
    
    public static Object fieldValue(Field field, Object obj) {
        // check and set the accessibility 
        if (!Modifier.isPublic(field.getModifiers()))
            field.setAccessible(true);
        
        Object value = null;
        
        // try to get the value
        try {
            value = field.get(obj);
        } catch (Exception e) {
            logger.error("Cannot read the value of given field.", e);
        }
        
        return value;
    }

}
