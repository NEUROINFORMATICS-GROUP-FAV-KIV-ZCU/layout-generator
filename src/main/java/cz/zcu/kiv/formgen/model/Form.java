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
    
    private boolean layout;
    
    private String layoutName;
    
    private Vector<FormItem> items = new Vector<FormItem>();
    
    
    
    public Form(String name) {
        super(name, Type.FORM);
    }
    
    
    @Override
    public void addItem(FormItem item) {
        
        if (item == null) {
            System.out.println("item je null");
            return;
        }
        
        if (item instanceof Form)
            highestItemId = Math.max(highestItemId, ((Form) item).highestItemId());
        else
            highestItemId = Math.max(highestItemId, item.getId());
        
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
    
    
    @Override
    public void setId(int id) {
        super.setId(id);
        if (id > highestItemId)
            highestItemId = id;
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


    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + highestItemId;
        result = prime * result + ((items == null) ? 0 : items.hashCode());
        result = prime * result + (layout ? 1231 : 1237);
        result = prime * result + ((layoutName == null) ? 0 : layoutName.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (!super.equals(obj)) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        Form other = (Form) obj;
        if (description == null) {
            if (other.description != null) { return false; }
        } else if (!description.equals(other.description)) { return false; }
        if (highestItemId != other.highestItemId) { return false; }
        if (items == null) {
            if (other.items != null) { return false; }
        } else if (!items.equals(other.items)) { return false; }
        if (layout != other.layout) { return false; }
        if (layoutName == null) {
            if (other.layoutName != null) { return false; }
        } else if (!layoutName.equals(other.layoutName)) { return false; }
        return true;
    }


}
