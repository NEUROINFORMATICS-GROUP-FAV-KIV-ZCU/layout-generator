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
 *     http://www.apache.org/licenses/LICENSE-2.0
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

import odml.core.Section;
import cz.zcu.kiv.formgen.FormItem;


/**
 *
 * @author Jakub Krauz
 */
public class OdmlFormItem extends Section implements FormItem {
    
    
    private static final long serialVersionUID = 1L;


    public OdmlFormItem(String name, Class<?> type) throws Exception {
        super(name, OdmlTypeMapper.instance().mapType(type));
        setDatatype(OdmlTypeMapper.instance().mapDatatype(type));
    }


    @Override
    public void setLabel(String label) {
        addProperty("label", label);
    }


    @Override
    public void setRequired(boolean required) {
        addProperty("required", required);
    }


    @Override
    public void setDatatype(String datatype) {
        addProperty("datatype", datatype);
    }

}
