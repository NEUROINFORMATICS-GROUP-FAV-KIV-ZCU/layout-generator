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
 * FormData.java, 2. 4. 2014 10:48:14 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;

import java.util.HashSet;
import java.util.Set;


/**
 *
 * @author Jakub Krauz
 */
public class FormData extends AbstractFormDataItem implements FormDataItem {
    
    public static final String SET = "set";
    
    private Object id;
    
    private Set<FormDataItem> items = new HashSet<FormDataItem>();
    
    
    public FormData(String type, String name) {
        super(type, name);
    }
    

    @Override
    public boolean isSimpleType() {
        return false;
    }
    
    
    public void addItem(FormDataItem item) {
        if (item == null)
            return;
        items.add(item);
    }
    
    
    public Set<FormDataItem> getItems() {
        return items;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
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
        if (items == null) {
            if (other.items != null) { return false; }
        } else if (!items.equals(other.items)) { return false; }
        return true;
    }


    
    /**
     * @return the id
     */
    public Object getId() {
        return id;
    }


    
    /**
     * @param id the id to set
     */
    public void setId(Object id) {
        this.id = id;
    }

}
