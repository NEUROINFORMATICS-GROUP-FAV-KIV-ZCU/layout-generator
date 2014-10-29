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
 * AbstractFormDataItem.java, 2. 4. 2014 10:52:11 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;


/**
 * Abstract implementation of the {@link FormDataItem} interface.
 *
 * @author Jakub Krauz
 */
public abstract class AbstractFormDataItem implements FormDataItem {
    
    /** Type of the data item. */
    protected String type;
    
    /** Name of the data item. */
    protected String name;
    
    /** Label of the item. */
    protected String label;
    
    
    /**
     * Creates a new data item with the given type and name.
     * 
     * @param type Type of the data item.
     * @param name Name of the data item.
     */
    protected AbstractFormDataItem(String type, String name) {
        this.type = type;
        this.name = name;
    }
    

    /**
     * {@inheritDoc}
     */
    @Override
    public void setType(String type) {
        this.type = type;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return type;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setLabel(String label) {
        this.label = label;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getLabel() {
        return label;
    }

    

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        AbstractFormDataItem other = (AbstractFormDataItem) obj;
        if (name == null) {
            if (other.name != null) { return false; }
        } else if (!name.equals(other.name)) { return false; }
        if (type == null) {
            if (other.type != null) { return false; }
        } else if (!type.equals(other.type)) { return false; }
        return true;
    }

}
