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
 * OdmlDataField.java, 26. 2. 2014 11:20:49 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml;

import java.util.Collection;
import cz.zcu.kiv.formgen.DataField;
import odml.core.Property;


/**
 *
 * @author Jakub Krauz
 */
public class OdmlDataField extends Property implements DataField {
    
    private static final long serialVersionUID = -3253266139533736224L;

    
    
    public OdmlDataField(String name, Object value) throws Exception {
        super(name, value);
    }
    
    
    @Override
    public void addValues(Collection<Object> values) {
        for (Object value : values)
            addValue(value);
    }
    
    
    
    
    

    @Override
    public void setId(int id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int getId() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setLabel(String label) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getLabel() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
