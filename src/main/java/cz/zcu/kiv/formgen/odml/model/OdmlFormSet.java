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

import odml.core.Property;
import odml.core.Section;
import cz.zcu.kiv.formgen.core.TypeMapper;
import cz.zcu.kiv.formgen.model.FormItem;
import cz.zcu.kiv.formgen.model.FormItemContainer;


/**
 *
 * @author Jakub Krauz
 */
public class OdmlFormSet extends OdmlFormField  {

    private static final long serialVersionUID = 1L;
    
    
    public OdmlFormSet(String name, Class<?> type) throws Exception {
        //super(name, TypeMapper.instance().mapType(type));
        super(null, null);
    }
    
    
    public void addItem(FormItem item) {
        if (item instanceof Section)
            add((Section) item);
        else if (item instanceof Property)
            add((Property) item);
        else
            ; // TODO log
    }

}
