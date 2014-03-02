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
 * ObjectParser.java, 26. 2. 2014 11:09:27 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import java.lang.reflect.Field;
import java.util.Collection;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.model.FormItem;
import cz.zcu.kiv.formgen.model.FormSet;


/**
 *
 * @author Jakub Krauz
 */
public class ObjectParser extends AbstractParser<Object> {
    
    
    /**
     * Parses the given POJO and creates a new {@link Form} model.
     * 
     * @param obj - the POJO to be parsed
     * @return the newly created {@link Form} object
     */
    @Override
    public Form parse(Object obj) {
        return _parse(obj, obj.getClass().getSimpleName());
    }
    
    
    /**
     * Parses the given POJO and adds it as a subform to an existing {@link Form} model
     * (used for multiple-class forms).
     * 
     * @param obj - the POJO to be parsed
     * @param form - the form in which the parsed POJO is to be added
     */
    @Override
    public void parse(Object obj, Form form) {
        form.addItem(_parse(obj, obj.getClass().getSimpleName()));
    }
    
    
    private Form _parse(Object obj, String formName) {
        if (obj == null)
            return null;
        
        Form form = new Form(formName);
        form.setLayout(false);
        
        for (Field f : formItemFields(obj.getClass())) {
            if (isSimpleType(f.getType())) {
                FormItem item = createDataField(f, obj);
                form.addItem(item);
            } else if (Collection.class.isAssignableFrom(f.getType())) {
                FormSet set = createDataSet(f, obj);
                form.addItem(set);
            } else {
                Form subform = _parse(fieldValue(f, obj), f.getName());
                form.addItem(subform);
            }
        }
        
        return form;
    }
    

    private FormSet createDataSet(Field field, Object obj) {
        String name = field.getName();
        
        Collection<?> collection = (Collection<?>) fieldValue(field, obj);
        if (collection == null || collection.isEmpty())
            return null;

        FormSet dataSet = new FormSet(name, TypeMapper.instance().mapType(collection.iterator().next().getClass()));

        int index = 0;
        for (Object o : collection) {
            if (isSimpleType(o.getClass())) {
                FormField formField = new FormField(name + "[" + index++ + "]", TypeMapper.instance().mapType(o.getClass()),
                        TypeMapper.instance().mapDatatype(o.getClass()));
                formField.setValue(o);
                dataSet.addItem(formField);
            } else {
                Form form = _parse(o, name + "[" + index++ + "]");
                dataSet.addItem(form);   
            }
        }
        
        return dataSet;
    }



    private FormField createDataField(Field f, Object obj) {
        Object value = fieldValue(f, obj);
        FormField dataField = new FormField(f.getName(), TypeMapper.instance().mapType(f.getType()),
                TypeMapper.instance().mapDatatype(f.getType()));
        dataField.setValue(value);
        return dataField;
    }
    
    
    private Object fieldValue(Field field, Object obj) {
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
