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
 * OdmlFormItem.java, 11. 2. 2014 17:46:35 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml.model;

import odml.core.Property;
import odml.core.Section;
import cz.zcu.kiv.formgen.model.FormItem;


/**
 *
 * @author Jakub Krauz
 */
public class OdmlFormItem extends Section implements FormItem {

    private static final long serialVersionUID = 1L;
    
    private int id = -1;
    
    
    public OdmlFormItem(String name, String type) throws Exception {
        super(name, type);
    }


    @Override
    public void setId(int id) {
        this.id = id;
        addProperty("id", id);
    }

    
    @Override
    public int getId() {
        // BUG v odml-java-lib??
        // pokud neni property predtim nastaveno, pada na stack overflow
        /*Property prop = getProperty("id");
        if (prop != null)
            return (Integer) prop.getValue();
        else
            return -1;*/
        return id;
    }


    @Override
    public void setLabel(String label) {
        addProperty("label", label);
    }


    @Override
    public String getLabel() {
        Property prop = getProperty("label");
        if (prop != null)
            return (String) prop.getValue();
        else
            return null;
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

}