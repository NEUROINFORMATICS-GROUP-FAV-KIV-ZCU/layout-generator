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
 * AbstractFormItem.java, 28. 2. 2014 16:57:30 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;

import cz.zcu.kiv.formgen.model.constraints.Cardinality;


/**
 * Abstract implementation of the {@link FormItem} interface.
 *
 * @author Jakub Krauz
 */
public abstract class AbstractFormItem implements FormItem {
    
    /** Type of the form item. */
    protected Type type;
    
    /** ID of the form item. */
    protected int id;
    
    /** Name of the form item. */
    protected String name;
    
    /** Label of the form item. */
    protected String label;
    
    /** Required flag of the form item. */
    protected boolean required;
    
    /** Cardinality of the form item. */
    protected Cardinality cardinality;
    
    
    
    /**
     * Constructs a new form item with the given name and type.
     * 
     * @param name The name of the form item.
     * @param type The type of the form item.
     */
    public AbstractFormItem(String name, Type type) {
        this.name = name;
        this.type = type;
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Type getType() {
        return type;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setType(Type type) {
        this.type = type;
    }
    

    /**
     * {@inheritDoc}
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getId() {
        return id;
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


    /**
     * {@inheritDoc}
     */
    @Override
    public void setRequired(boolean required) {
        this.required = required;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRequired() {
        return required;
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setCardinality(Cardinality cardinality) {
        this.cardinality = cardinality;
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Cardinality getCardinality() {
        return cardinality;
    }


    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cardinality == null) ? 0 : cardinality.hashCode());
        result = prime * result + id;
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + (required ? 1231 : 1237);
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
        AbstractFormItem other = (AbstractFormItem) obj;
        if (cardinality == null) {
            if (other.cardinality != null) { return false; }
        } else if (!cardinality.equals(other.cardinality)) { return false; }
        if (id != other.id) { return false; }
        if (label == null) {
            if (other.label != null) { return false; }
        } else if (!label.equals(other.label)) { return false; }
        if (name == null) {
            if (other.name != null) { return false; }
        } else if (!name.equals(other.name)) { return false; }
        if (required != other.required) { return false; }
        if (type != other.type) { return false; }
        return true;
    }
 
    
}
