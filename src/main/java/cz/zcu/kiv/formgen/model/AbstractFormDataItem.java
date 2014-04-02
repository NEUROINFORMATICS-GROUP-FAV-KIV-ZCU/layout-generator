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
 * AbstractFormDataItem.java, 2. 4. 2014 10:52:11 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;


/**
 *
 * @author Jakub Krauz
 */
public abstract class AbstractFormDataItem implements FormDataItem {
    
    protected String type;
    
    protected String name;
    
    
    
    protected AbstractFormDataItem(String type, String name) {
        this.type = type;
        this.name = name;
    }
    

    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.model.FormDataItem#setType(java.lang.String)
     */
    @Override
    public void setType(String type) {
        this.type = type;
    }


    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.model.FormDataItem#getType()
     */
    @Override
    public String getType() {
        return type;
    }


    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.model.FormDataItem#setName(java.lang.String)
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }


    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.model.FormDataItem#getName()
     */
    @Override
    public String getName() {
        return name;
    }

}
