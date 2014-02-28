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
 * OdmlForm.java, 1. 12. 2013 19:11:18 Jakub Krauz
 * 
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml.model;

import odml.core.Property;
import odml.core.Section;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormItem;
import cz.zcu.kiv.formgen.model.FieldType;


/**
 * Form in odML layout.
 * 
 * @author Jakub Krauz
 */
public class OdmlForm extends OdmlFormItem {

    public static final String TYPE = FieldType.FORM.getValue();

    private static final long serialVersionUID = 1L;

    private int highestItemId = 0;
    
    private int lastItemId = 0;
    
    private String layoutName;


    public OdmlForm(String name) throws Exception {
        super(name, TYPE);
    }


    public void addItem(FormItem item) {
        /*if (item instanceof DataField) {
            addDataField((DataField) item);
        } else {*/
            if (item instanceof OdmlForm)
                highestItemId = Math.max(highestItemId, ((OdmlForm) item).highestItemId());
            else
                highestItemId = Math.max(highestItemId, item.getId());
            
            if (lastItemId != 0)
                ((OdmlFormItem) item).addProperty("idTop", lastItemId);
            lastItemId = item.getId();
            
            add((Section) item);
       // }
    }
    
    
    public void setFormName(String name) {
        setName(name);
    }
    
    

    public String getFormName() {
        return getName();
    }



    public void setDescription(String description) {
        setDefinition(description);
    }



    public String getDescription() {
        return getDefinition();
    }
    
    

    public void setId(int id) {
        super.setId(id);
        if (id > highestItemId)
            highestItemId = id;
    }



    public int highestItemId() {
        return highestItemId;
    }



    public void setLayoutName(String name) {
        this.layoutName = name;
        addProperty("layoutName", name);
    }
    
    

    public String getLayoutName() {
        return layoutName;
    }
    
    
    
    /*private void addDataField(DataField field) {
        add((Property) field);
    }*/

}
