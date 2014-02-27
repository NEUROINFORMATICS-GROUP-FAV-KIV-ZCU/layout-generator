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
 * FormItem.java, 1. 12. 2013 19:10:07 Jakub Krauz
 * 
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;


/**
 * Represents a form item.
 * 
 * <p>
 * Every {@link Form} consists of items and/or subforms. This class represents an item that can be added to a form. An
 * item can be set a label and a required-flag. Label is a text that should appear in the form right in front of the
 * field that allows user to set the value of the item. If the required-flag is true then the item must be set in every
 * form (cannot be left blank).
 * </p>
 * 
 * @author Jakub Krauz
 */
public interface FormField extends FormItem {


    /**
     * Sets the datatype property to this form item.
     * 
     * @param datatype - the datatype name
     */
    void setDatatype(String datatype);


    /**
     * Gets the datatype of this form item.
     * 
     * @return the datatype
     */
    String getDatatype();


    /**
     * Sets the minimum length of this form item.
     * 
     * @param value the minimum length
     */
    void setMinLength(int value);


    /**
     * Gets the minimum length of this form item.
     * 
     * @return the minimum length
     */
    int getMinLength();


    /**
     * Sets the maximum length of this form item.
     * 
     * @param value the maximum length
     */
    void setMaxLength(int value);


    /**
     * Gets the maximum length of this form item.
     * 
     * @return the maximum length
     */
    int getMaxLength();


    /**
     * Sets the minimum value permitted for this item (number fields only).
     * 
     * @param value the minimum value
     */
    void setMinValue(Object value);

    
    /**
     * Gets the minimum value of the number field.
     * 
     * @return the minimum value
     */
    Number getMinValue();


    /**
     * Sets the maximum value permitted for this item (number fields only).
     * 
     * @param value the maximum value
     */
    void setMaxValue(Number value);


    /**
     * Gets the maximum value of the number field.
     * 
     * @return the maximum value
     */
    Number getMaxValue();


    /**
     * Sets the default value of this field.
     * 
     * @param value the default value
     */
    void setDefaultValue(Object value);


    /**
     * Gets the default value.
     * 
     * @return the default value
     */
    Object getDefaultValue();


    /**
     * Sets enumeration of possible values.
     * 
     * @param values possible values
     */
    void setPossibleValues(Object[] values);

    
    /**
     * Gets the enumeration of possible values.
     * 
     * @return possible values
     */
    Object[] getPossibleValues();


}
