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
 * OdmlFormSet.java, 23. 1. 2014 14:45:22 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml.model;

import odml.core.Section;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.model.FormSet;
import cz.zcu.kiv.formgen.odml.OdmlTypeMapper;


/**
 *
 * @author Jakub Krauz
 */
public class OdmlFormSet extends OdmlFormField implements FormSet {

    private static final long serialVersionUID = 1L;
    
    
    public OdmlFormSet(String name, Class<?> type) throws Exception {
        super(name, OdmlTypeMapper.instance().mapType(type));
    }


    @Override
    public void setContent(Form form) {
        subsections.removeAllElements();
        add((Section) form);
    }


    @Override
    public void setContent(FormField item) {
        subsections.removeAllElements();
        add((Section) item);
    }


    @Override
    public void setDatatype(String datatype) {
        // TODO setdatatype u formset nema vyznam
        addProperty("datatype", datatype);
    }

}
