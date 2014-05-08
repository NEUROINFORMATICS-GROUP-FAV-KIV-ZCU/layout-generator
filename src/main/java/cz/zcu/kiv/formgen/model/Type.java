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
 * Type.java, 15. 11. 2013 19:14:06 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;


/**
 * Type of a form item.
 * 
 * <p>
 * The type determines the meaning and graphical representation of the item.
 * It can be either a whole form, or some input fields like textboxes, checkboxes and so on.
 * </p>
 *
 * @author Jakub Krauz
 */
public enum Type {
    
    FORM       ("form"), 
    TEXTBOX    ("textbox"), 
    CHECKBOX   ("checkbox"),
    COMBOBOX   ("combobox"),
    CHOICE     ("choice"),
    IMAGE      ("image");
    
    
    /** The string value used for serialization. */
    private String value;
    
    
    /**
     * Constructs a new Type with the given value.
     * @param value the string value
     */
    private Type(String value) {
        this.value = value;
    }
    
    
    @Override
    public String toString() {
        return value;
    }
    
    
    /**
     * Returns Type instance corresponding to the given string value.
     * 
     * @param value The string value.
     * @return The corresponding Type, or null if the value was not recognized. 
     */
    public static Type fromString(String value) {
        if (FORM.toString().equals(value))
            return FORM;
        if (TEXTBOX.toString().equals(value))
            return TEXTBOX;
        if (CHECKBOX.toString().equals(value))
            return CHECKBOX;
        if (COMBOBOX.toString().equals(value))
            return COMBOBOX;
        if (CHOICE.toString().equals(value))
            return CHOICE;
        if (IMAGE.toString().equals(value))
            return IMAGE;
        return null;
    }


}
