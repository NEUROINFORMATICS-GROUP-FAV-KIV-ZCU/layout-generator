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
 * DataParser.java, 2. 4. 2014 11:36:46 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import java.lang.reflect.Field;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cz.zcu.kiv.formgen.annotation.FormId;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormData;
import cz.zcu.kiv.formgen.model.FormDataField;
import cz.zcu.kiv.formgen.model.FormDataItem;


/**
 *
 * @author Jakub Krauz
 */
public class DataParser {
    
    /** Type mapper object. */
    private final TypeMapper mapper = new TypeMapper();
    
    /** Logger. */
    final Logger logger = LoggerFactory.getLogger(DataParser.class);
    
    // TODO jen testovaci
    private int counter = 0;
    
    
    /**
     * Parses the given POJO and creates a new {@link FormData} model.
     * 
     * @param obj - the POJO to be parsed
     * @return the newly created {@link Form} object
     */
    public FormData parse(Object obj) {
        return _parse(obj, obj.getClass().getSimpleName() + "_" + (++counter));
    }
    
    
    private FormData _parse(Object obj, String formName) {
        if (obj == null)
            return null;
        
        FormData form = new FormData(obj.getClass().getSimpleName(), formName);
        
        Field idField = ReflectionUtils.annotatedField(obj.getClass(), FormId.class);
        if (idField != null) {
            idField.setAccessible(true);
            form.setId(ReflectionUtils.fieldValue(idField, obj));
        }
        
        for (Field f : ReflectionUtils.annotatedFields(obj.getClass(), cz.zcu.kiv.formgen.annotation.FormItem.class)) {
            if (mapper.isSimpleType(f.getType())) {
                FormDataItem item = createDataField(f, obj);
                form.addItem(item);
            } else if (Collection.class.isAssignableFrom(f.getType())) {
                FormDataItem set = createDataSet(f, obj);
                form.addItem(set);
            } else {
                FormData subform = _parse(ReflectionUtils.fieldValue(f, obj), f.getName());
                form.addItem(subform);
            }
        }
        
        return form;
    }
    

    private FormDataItem createDataSet(Field field, Object obj) {
        String name = field.getName();
        
        Collection<?> collection = (Collection<?>) ReflectionUtils.fieldValue(field, obj);
        if (collection == null || collection.isEmpty())
            return null;
        
        FormData dataSet = new FormData("set", field.getName());

        int index = 0;
        for (Object o : collection) {
            if (mapper.isSimpleType(o.getClass())) {
                FormDataField formField = new FormDataField(o.getClass().getSimpleName(), name + "[" + index++ + "]");
                formField.setValue(o);
                dataSet.addItem(formField);
            } else {
                FormData form = _parse(o, name + "[" + index++ + "]");
                dataSet.addItem(form);   
            }
        }
        
        return dataSet;
    }



    private FormDataField createDataField(Field f, Object obj) {
        Object value = ReflectionUtils.fieldValue(f, obj);
        FormDataField dataField = new FormDataField(f.getType().getSimpleName(), f.getName());
        dataField.setValue(value);
        return dataField;
    }
   

}
