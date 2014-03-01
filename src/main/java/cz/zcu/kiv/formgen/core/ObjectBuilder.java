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
import java.lang.reflect.Method;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.model.FormItem;


/**
 *
 * @author Jakub Krauz
 */
public class ObjectBuilder<T> {
    
    private Class<T> type;
    
    
    public ObjectBuilder(Class<T> type) {
        this.type = type;
    }
    
    
    public T build(Form form) {
        T obj = null;
        
        try {
            obj = type.newInstance();
            fill(obj, form);
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return obj;
    }
    
    
    
    private void fill(Object obj, Form form) {
        try {
        
            for (FormItem item : form.getItems()) {
                System.out.println("parsing " + item.getName());
                if (item instanceof FormField) {
                    /*System.out.println("setting " + item.getName() + " = " + ((FormField) item).getValue()
                            + "  (" + ((FormField) item).getValue().getClass() + ")");*/
                    Field field = obj.getClass().getDeclaredField(item.getName());
                    field.setAccessible(true);
                    field.set(obj, ((FormField) item).getValue());
                } else if (item instanceof Form) {
                    Field field = obj.getClass().getDeclaredField(item.getName());
                    Object o = field.getType().newInstance();
                    fill(o, (Form) item);
                    field.setAccessible(true);
                    field.set(obj, o);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
