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
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.model.FormItem;
import cz.zcu.kiv.formgen.model.FormSet;


/**
 *
 * @author Jakub Krauz
 */
public class ObjectBuilder<T> {
    
    final Logger logger = LoggerFactory.getLogger(ObjectBuilder.class);
    
    private Class<T> type;
    
    
    public ObjectBuilder(Class<T> type) {
        this.type = type;
    }
    
    
    public T build(Form form) throws ObjectBuilderException {
        T obj = null;
        
        try {
            obj = type.newInstance();
            fill(obj, form);
        } catch (Exception e) {
            logger.error("Can not create instance of " + type.getCanonicalName(), e);
            throw new ObjectBuilderException("Can not create instance of " + type.getCanonicalName(), e);
        }
        
        return obj;
    }
    
    
    
    private void fill(Object obj, Form form) throws SecurityException, NoSuchFieldException, 
                        IllegalArgumentException, IllegalAccessException, InstantiationException {
        
        for (FormItem item : form.getItems()) {
            Field field = obj.getClass().getDeclaredField(item.getName());
            field.setAccessible(true);
            if (item instanceof FormField) {
                Object value = ((FormField) item).getValue();
                
                // convert to appropriate number type
                if (value instanceof Number) {
                    Class<?> type = TypeMapper.toPrimitiveType(field.getType());
                    if (type.equals(Byte.TYPE))
                        value = ((Number) value).byteValue();
                    else if (type.equals(Short.TYPE))
                        value = ((Number) value).shortValue();
                    else if (type.equals(Integer.TYPE))
                        value = ((Number) value).intValue();
                    else if (type.equals(Long.TYPE))
                        value = ((Number) value).longValue();
                    else if (type.equals(Float.TYPE))
                        value = ((Number) value).floatValue();
                    else if (type.equals(Double.TYPE))
                        value = ((Number) value).doubleValue();
                }
                
                field.set(obj, value);
            } else if (item instanceof Form) {
                Object o = field.getType().newInstance();
                fill(o, (Form) item);
                field.set(obj, o);
            } else if (item instanceof FormSet) {
                Collection<?> collection = (Collection<?>) field.get(obj);
                if (collection != null)
                    ; // TODO add items
                else
                    ; // TODO create collection 
            }
        }

    }

    
}
