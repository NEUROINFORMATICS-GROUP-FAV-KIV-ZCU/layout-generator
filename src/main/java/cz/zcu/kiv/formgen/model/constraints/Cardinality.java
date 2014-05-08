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
 * Cardinality.java, 2. 4. 2014 9:22:42 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model.constraints;


/**
 * Cardinality of a form item.
 * 
 * <p>
 * The cardinality constraint determines how many values can be set for the item.
 * Ordinary fields usually allow only one value, while some special items can have
 * multiple values (without restraining the maximum count). For these standard cases
 * {@link #SINGLE_VALUE} and {@link #UNRESTRICTED} constants can be used respectively.
 * </p>
 * 
 * @author Jakub Krauz
 */
public class Cardinality implements Constraint {
    
    /** The most common case, allowing only one input value for an item. */
    public static final Cardinality SINGLE_VALUE = new Cardinality(1);
    
    /** This value permits an arbitrary number of input values for an item. */
    public static final Cardinality UNRESTRICTED = new Cardinality(-1);
    
    /** The cardinality value. */
    private int value;
    
    
    /**
     * Constructs a new cardinality object with the given value.
     * @param value the value
     */
    public Cardinality(int value) {
        this.value = value;
    }
    
    
    /**
     * Returns the cardinality value.
     * @return the cardinality
     */
    public int getValue() {
        return value;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String name() {
        return "cardinality";
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Object value() {
        return (Object) value;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + value;
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
        Cardinality other = (Cardinality) obj;
        if (value != other.value) { return false; }
        return true;
    }

}
