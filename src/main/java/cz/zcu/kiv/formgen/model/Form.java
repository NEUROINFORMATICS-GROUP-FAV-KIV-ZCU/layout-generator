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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
public class Form extends AbstractFormItem implements FormItem {
    
    /** Logger. */
    final Logger logger = LoggerFactory.getLogger(Form.class);
    
    /** Description of the form. */
    private String description;
    
    /** The highest of IDs of contained items. */
    private int highestItemId = 0;
    
    /** Name of the layout. */
    private String layoutName;
    
    private String dataReference;
    
    /** Contained items. */
    private Vector<FormItem> items = new Vector<FormItem>();
    
    
    
    /**
     * Constructor.
     * Creates a new form object with the specified name.
     * @param name
     */
    public Form(String name) {
        super(name, Type.FORM);
    }
    
    
    
    /**
     * Adds the given item to the end of this form.
     * 
     * @param item - the item to be added
     */
    public void addItem(FormItem item) {
        if (item == null) {
            logger.debug("Form \"{}\": attempt to add a null item.", this.name);
            return;
        }
        
        // update the highestItemId
        if (item instanceof Form)
            highestItemId = Math.max(highestItemId, ((Form) item).highestItemId());
        else
            highestItemId = Math.max(highestItemId, item.getId());
        
        // add the item
        items.add(item);
    }
    
    
    /**
     * Gets all items of the form.
     * 
     * @return vector of contained items
     */ 
    public Vector<FormItem> getItems() {
        return items;
    }
    
    
    /**
     * Returns the number of contained items.
     * 
     * @return number of items
     */
    public int countItems() {
        return items.size();
    }
    
    
    /**
     * Returns item on the given position (indexed from 0) or null.
     * 
     * @param index - the index of the position
     * @return item on the specified position or null
     */
    public FormItem getItemAt(int position) {
        try {
            return items.get(position);
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }
    
        
    @Override
    public void setId(int id) {
        super.setId(id);
        if (id > highestItemId)
            highestItemId = id;
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
    
    
    /**
     * @return the dataReference
     */
    public String getDataReference() {
        return dataReference;
    }

 
    /**
     * @param dataReference the dataReference to set
     */
    public void setDataReference(String dataReference) {
        this.dataReference = dataReference;
    }


    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + highestItemId;
        result = prime * result + ((items == null) ? 0 : items.hashCode());
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
        if (layoutName == null) {
            if (other.layoutName != null) { return false; }
        } else if (!layoutName.equals(other.layoutName)) { return false; }
        return true;
    }


}
