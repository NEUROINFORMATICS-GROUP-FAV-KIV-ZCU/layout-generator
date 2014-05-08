/***********************************************************************************************************************
 *
 * This file is part of the layout-generator project
 *
 * =================================================
 *
 * Copyright (C) 2013 by University of West Bohemia (http://www.zcu.cz/en/)
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
 * FieldDatatype.java, 18. 12. 2013 16:11:45 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;


/**
 * Datatypes of form fields input values.
 *
 * @author Jakub Krauz
 */
public enum FieldDatatype {
    
    STRING      ("string"),
    INTEGER     ("integer"),
    NUMBER      ("number"),
    DATE        ("date"),
    EMAIL       ("email");
    
    
    /** The string value used for serialization. */
    private String value;
    
    
    /**
     * Creates a new instance with the given {@link #value}.
     * @param value The value to be set.
     */
    private FieldDatatype(String value) {
        this.value = value;
    }
    
    
    @Override
    public String toString() {
        return value;
    }
    
    
    /**
     * Returns FieldDatatype for the given string value.
     * 
     * @param value The value.
     * @return The corresponding FieldDatatype, or null if the value is not recognized.
     */
    public static FieldDatatype fromString(String value) {
        if (STRING.value.equals(value))
            return STRING;
        if (INTEGER.value.equals(value))
            return INTEGER;
        if (NUMBER.value.equals(value))
            return NUMBER;
        if (DATE.value.equals(value))
            return DATE;
        if (EMAIL.value.equals(value))
            return EMAIL;
        return null;
    }
    

}
