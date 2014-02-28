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
 * Form.java, 1. 12. 2013 19:07:32 Jakub Krauz
 * 
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;

import java.util.Vector;


/**
 * Represents a form.
 * 
 * <p>
 * Form consists of items (type {@link FormField}) and subforms of the same type. Items and subforms can be added to the
 * form using {@link #addItem(FormField)} or {@link #addSubform(Form)} respectively. Every form has its name, which
 * should be assigned during construction. A form can be assigned a description which is not compulsory.
 * </p>
 * 
 * @author Jakub Krauz
 */
public class Form extends AbstractFormItem implements FormItem, FormItemContainer {
    
    private String description;
    
    private int highestItemId = 0;
    
    private int lastItemId = 0;
    
    private boolean layout;
    
    private String layoutName;
    
    private Vector<FormItem> items = new Vector<FormItem>();
    
    
    
    public Form(String name) {
        this.name = name;
    }
    
    
    @Override
    public void addItem(FormItem item) {
        
        if (item == null) {
            System.out.println("item je null");
            return;
        }
        
        /*if (item instanceof DataField) {
            addDataField((DataField) item);
        } else {
            if (item instanceof OdmlForm)
                highestItemId = Math.max(highestItemId, ((OdmlForm) item).highestItemId());
            else
                highestItemId = Math.max(highestItemId, item.getId());
            
            if (lastItemId != 0)
                ((OdmlFormItem) item).addProperty("idTop", lastItemId);
            lastItemId = item.getId();
            
            add((Section) item);
        }*/
        
        if (item instanceof Form)
            highestItemId = Math.max(highestItemId, ((Form) item).highestItemId());
        else
            highestItemId = Math.max(highestItemId, item.getId());

        lastItemId = item.getId();
        
        items.add(item);
    }
    
    
    @Override 
    public Vector<FormItem> getItems() {
        return items;
    }
    
    
    /**
     * Returns the highest ID of contained form items.
     * 
     * @return the highest ID
     */
    public int highestItemId() {
        return highestItemId;
    }



    /**
     * Sets the given description to this form.
     * 
     * @param description - the description for this form
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    
    /**
     * Gets the form's description.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    
    /**
     * Sets the name of the form layout.
     * 
     * @param name the name of the layout
     */
    public void setLayoutName(String name) {
        this.layoutName = name;
    }
    
    
    /**
     * Gets the name of the form layout.
     * 
     * @return the name of the form layout
     */
    public String getLayoutName() {
        return layoutName;
    }


    
    public boolean isLayout() {
        return layout;
    }


    
    public void setLayout(boolean layout) {
        this.layout = layout;
    }


}
