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
 * FormDataField.java, 2. 4. 2014 10:48:41 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;


/**
 * Represents a simple data value, i.e. input related to a form field.
 *
 * @author Jakub Krauz
 */
public class FormDataField extends AbstractFormDataItem {
    
    /** The value. */
    private Object value;
    
    
    /**
     * Constructs a new data field with the given name.
     * 
     * @param name The name of the data field.
     */
    public FormDataField(String name) {
        super(null, name);
    }
    
    
    /**
     * Constructs a new data field with the given name and value.
     * 
     * @param name The name of the data field.
     * @param value The value of the data field.
     */
    public FormDataField(String name, Object value) {
        super(null, name);
        this.value = value;
    }

    
    /**
     * Returns the value.
     * 
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    
    /**
     * Sets the value.
     * 
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSimpleType() {
        return true;
    }


    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        FormDataField other = (FormDataField) obj;
        if (value == null) {
            if (other.value != null) { return false; }
        } else if (!value.equals(other.value)) { return false; }
        return true;
    }

}
