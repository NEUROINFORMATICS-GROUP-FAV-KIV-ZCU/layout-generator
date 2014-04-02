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
 * FormDataField.java, 2. 4. 2014 10:48:41 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;


/**
 *
 * @author Jakub Krauz
 */
public class FormDataField extends AbstractFormDataItem implements FormDataItem {
    
    private Object value;
    
    
    public FormDataField(String type, String name) {
        super(type, name);
    }

    
    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    
    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }


    @Override
    public boolean isSimpleType() {
        return true;
    }

}
