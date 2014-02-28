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
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 *********************************************************************************************************************** 
 * 
 * OdmlFormItem.java, 1. 12. 2013 19:12:50 Jakub Krauz
 * 
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml.model;

import odml.core.Property;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.odml.OdmlTypeMapper;
import cz.zcu.kiv.formgen.odml.SectionType;


/**
 * 
 * @author Jakub Krauz
 */
public class OdmlFormField extends OdmlFormItem {
    
    private static final int COMBOBOX_MAX_ITEMS = 5;

    private static final long serialVersionUID = 1L;


    public OdmlFormField(String name, Class<?> type) throws Exception {
        super(name, OdmlTypeMapper.instance().mapType(type));
        setDatatype(OdmlTypeMapper.instance().mapDatatype(type));
    }


    protected OdmlFormField(String name, String type) throws Exception {
        super(name, type);
    }



    public void setDatatype(String datatype) {
        addProperty("datatype", datatype);
    }



    public String getDatatype() {
        Property prop = getProperty("datatype");
        if (prop != null)
            return (String) prop.getValue();
        else
            return null;
    }



    public void setMinLength(int value) {
        addProperty("minLength", value);
    }



    public int getMinLength() {
        Property prop = getProperty("minLength");
        if (prop != null)
            return (Integer) prop.getValue();
        else
            return -1;
    }



    public void setMaxLength(int value) {
        addProperty("maxLength", value);
    }



    public int getMaxLength() {
        Property prop = getProperty("maxLength");
        if (prop != null)
            return (Integer) prop.getValue();
        else
            return -1;
    }



    public void setMinValue(Object value) {
        addProperty("minValue", value);
    }



    public Number getMinValue() {
        Property prop = getProperty("minValue");
        if (prop != null)
            return (Number) prop.getValue();
        else
            return null;
    }



    public void setMaxValue(Number value) {
        addProperty("maxValue", value);
    }



    public Number getMaxValue() {
        Property prop = getProperty("maxValue");
        if (prop != null)
            return (Number) prop.getValue();
        else
            return null;
    }



    public void setDefaultValue(Object value) {
        addProperty("defaultValue", value);
    }



    public Object getDefaultValue() {
        Property prop = getProperty("defaultValue");
        if (prop != null)
            return (Object) prop.getValue();
        else
            return null;
    }



    public void setPossibleValues(Object[] values) {
        if (values.length <= COMBOBOX_MAX_ITEMS)
            setType(SectionType.COMBOBOX.getValue());
        else
            setType(SectionType.CHOICE.getValue());
        
        try {
            Property prop = new Property("values");
            for (Object o : values)
                prop.addValue(o);
            add(prop);
        } catch (Exception e) {
            // exception is never thrown
            e.printStackTrace();
        }
    }



    public Object[] getPossibleValues() {
        Property prop = getProperty("values");
        if (prop != null)
            return prop.getValues().toArray();
        else
            return null;
    }


}
