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

package cz.zcu.kiv.formgen.odml;

import odml.core.Property;
import cz.zcu.kiv.formgen.FormField;


/**
 * 
 * @author Jakub Krauz
 */
public class OdmlFormField extends OdmlFormItem implements FormField {


    private static final long serialVersionUID = 1L;


    public OdmlFormField(String name, Class<?> type) throws Exception {
        super(name, OdmlTypeMapper.instance().mapType(type));
        setDatatype(OdmlTypeMapper.instance().mapDatatype(type));
    }
    
    protected OdmlFormField(String name, String type) throws Exception {
        super(name, type);
    }


    @Override
    public void setRequired(boolean required) {
        addProperty("required", required);
    }


    @Override
    public boolean isRequired() {
        Property prop = getProperty("required");
        if (prop != null)
            return (Boolean) prop.getValue();
        else
            return false;
    }


    @Override
    public void setDatatype(String datatype) {
        addProperty("datatype", datatype);
    }


    @Override
    public String getDatatype() {
        Property prop = getProperty("datatype");
        if (prop != null)
            return (String) prop.getValue();
        else
            return null;
    }


    @Override
    public void setMinLength(int value) {
        addProperty("min-length", value);
    }


    @Override
    public int getMinLength() {
        Property prop = getProperty("min-length");
        if (prop != null)
            return (Integer) prop.getValue();
        else
            return -1;
    }


    @Override
    public void setMaxLength(int value) {
        addProperty("max-length", value);
    }


    @Override
    public int getMaxLength() {
        Property prop = getProperty("max-length");
        if (prop != null)
            return (Integer) prop.getValue();
        else
            return -1;
    }
    

}
