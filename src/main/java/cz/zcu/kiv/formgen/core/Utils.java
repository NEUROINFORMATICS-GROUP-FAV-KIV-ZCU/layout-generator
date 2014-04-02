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
 * Utils.java, 3. 3. 2014 18:14:28 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Jakub Krauz
 */
public class Utils {
    
    static final Logger logger = LoggerFactory.getLogger(Utils.class);
    
    
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
    
    
    /**
     * Returns collection of fields annotated by the {@link cz.zcu.kiv.formgen.annotation.FormItem FormItem} annotation.
     * 
     * @param cls - the class
     * @return collection of annotated fields
     */
    public static Collection<Field> formItemFields(Class<?> cls) {
        Collection<Field> collection = new Vector<Field>();
        
        for (Field f : cls.getDeclaredFields()) {
            if (f.isAnnotationPresent(cz.zcu.kiv.formgen.annotation.FormItem.class))
                collection.add(f);
        }
        
        return collection;
    }
    
    
    public static Object fieldValue(Field field, Object obj) {
        field.setAccessible(true);
        Object value = null;
        
        try {
            value = field.get(obj);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return value;
    }

}
