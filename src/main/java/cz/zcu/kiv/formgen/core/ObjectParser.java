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
import cz.zcu.kiv.formgen.ModelProvider;
import cz.zcu.kiv.formgen.model.DataField;
import cz.zcu.kiv.formgen.model.DataSet;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormItem;


/**
 *
 * @author Jakub Krauz
 */
public class ObjectParser extends AbstractParser<Object> {
    
    
    public ObjectParser(ModelProvider formProvider) {
        super(formProvider);
    }


    
    @Override
    protected Form _parse(Object obj) {
        if (obj == null)
            return null;
        
        Form form = createForm(obj.getClass());
        
        for (Field f : formItemFields(obj.getClass())) {
            if (isSimpleType(f.getType())) {
                FormItem item = createDataField(f, obj);
                form.addItem(item);
            } else if (Collection.class.isAssignableFrom(f.getType())) {
                Collection<?> collection = (Collection<?>) fieldValue(f, obj);
                if (collection == null || collection.isEmpty())
                    continue;
                if (isSimpleType(collection.iterator().next().getClass()))
                    form.addItem(createDataField(f, obj));
                else
                    form.addItem(createDataSet(f, obj));
            } else {
                Form subform = _parse(fieldValue(f, obj));
                form.addItem(subform);
            }
        }
        
        return form;
    }
    
    
    private DataSet createDataSet(Field field, Object obj) {
        String name = field.getName();
        DataSet dataSet = formProvider.newDataSet(name);
        
        Object value = fieldValue(field, obj);
        int index = 0;
        for (Object o : (Collection<?>) value) {
            Form form = _parse(o);
            form.setFormName(name + "[" + index++ + "]");
            dataSet.addItem(form);
        }
        
        return dataSet;
    }


    @SuppressWarnings("unchecked")
    private DataField createDataField(Field f, Object obj) {
        f.setAccessible(true);
        Object value = fieldValue(f, obj);
        DataField dataField;
        
        if (value instanceof Collection<?>) {
            dataField = formProvider.newDataField(f.getName(), null);
            dataField.addValues((Collection<Object>) value);
        } else {
            dataField = formProvider.newDataField(f.getName(), value);
        }
        
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
