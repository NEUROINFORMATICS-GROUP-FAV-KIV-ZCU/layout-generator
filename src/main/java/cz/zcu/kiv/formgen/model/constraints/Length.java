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
 * Length.java, 3. 4. 2014 18:11:46 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model.constraints;


/**
 * Restricts the length of a form field input.
 * 
 * @author Jakub Krauz
 */
public class Length implements Constraint {
    
    /** The name of this constraint. */
    private String name;
    
    /** The value (length). */
    private int value;
    
    
    /**
     * Constructs a new length constraint.
     * @param name the name
     * @param value the value
     */
    private Length(String name, int value) {
        this.name = name;
        this.value = value;
    }
    
    
    /**
     * Creates a new maximum length constraint.
     * @param value the maximum length
     * @return the constraint object
     */
    public static Length MAX(int value) {
        return new Length("maxLength", value);
    }
    
    
    /**
     * Creates a new minimum length constraint.
     * @param value the minimum length
     * @return the constraint object
     */
    public static Length MIN(int value) {
        return new Length("minLength", value);
    }
    

    /**
     * {@inheritDoc}
     */
    @Override
    public String name() {
        return name;
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
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Length other = (Length) obj;
        if (name == null) {
            if (other.name != null) { return false; }
        } else if (!name.equals(other.name)) { return false; }
        if (value != other.value) { return false; }
        return true;
    }

}
