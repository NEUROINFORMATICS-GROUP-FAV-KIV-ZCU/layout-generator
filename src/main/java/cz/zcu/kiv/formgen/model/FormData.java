/***********************************************************************************************************************
 *
 * This file is part of the layout-generator project
 *
 * =================================================
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
 * FormData.java, 2. 4. 2014 10:48:14 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;

import java.util.HashSet;
import java.util.Set;


/**
 * Represents a data record related to some form.
 *
 * @author Jakub Krauz
 */
public class FormData extends AbstractFormDataItem implements FormDataItem {
    
    /** The type for set of values. */
    public static final String SET = "set";
    
    /** ID of the record. */
    private Object id;
    
    /** Items contained in this data record. */
    private Set<FormDataItem> items = new HashSet<FormDataItem>();
    
    
    /**
     * Constructs a new data record with the given type and name.
     * 
     * @param type The type of the data record.
     * @param name The name of the data record.
     */
    public FormData(String type, String name) {
        super(type, name);
    }
    

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSimpleType() {
        return false;
    }
    
    
    /**
     * Adds a new data item to this record.
     * 
     * @param item The data item to be added.
     */
    public void addItem(FormDataItem item) {
        if (item == null)
            return;
        items.add(item);
    }
    
    
    /**
     * Returns all data items contained in this record.
     * 
     * @return set of contained data items
     */
    public Set<FormDataItem> getItems() {
        return items;
    }
    
    
    /**
     * Returns this record's ID.
     * 
     * @return the ID
     */
    public Object getId() {
        return id;
    }


    /**
     * Sets the ID of this record.
     * 
     * @param id the ID to set
     */
    public void setId(Object id) {
        this.id = id;
    }

    

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((items == null) ? 0 : items.hashCode());
        return result;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (!super.equals(obj)) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        FormData other = (FormData) obj;
        if (id == null) {
            if (other.id != null) { return false; }
        } else if (!id.equals(other.id)) { return false; }
        if (items == null) {
            if (other.items != null) { return false; }
        } else if (!items.equals(other.items)) { return false; }
        return true;
    }

}
