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

import cz.zcu.kiv.formgen.core.TypeMapper;


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
public class FormField extends AbstractFormItem implements FormItem {
    
    private FieldType type;
    
    private FieldDatatype datatype;
    
    private int minLength = -1;
    
    private int maxLength = -1;
    
    private Number minValue;
    
    private Number maxValue;
    
    private Object defaultValue;
    
    private Object possibleValues[];
    
    private Object value;
    
    
    public FormField(String name) {
        this(name, null, null);
    }
    
    
    public FormField(String name, FieldType type, FieldDatatype datatype) {
        this.name = name;
        this.type = type;
        this.datatype = datatype;
    }
    
    
    /*public FormField(String name, Class<?> type) {
        this.name = name;
        this.type = TypeMapper.instance().mapType(type);
        this.datatype = TypeMapper.instance().mapDatatype(type);
    }*/


    
    public FieldType getType() {
        return type;
    }


    
    public void setType(FieldType type) {
        this.type = type;
    }


    /**
     * Sets the datatype property to this form item.
     * 
     * @param datatype - the datatype name
     */
    public void setDatatype(FieldDatatype datatype) {
        this.datatype = datatype;
    }


    /**
     * Gets the datatype of this form item.
     * 
     * @return the datatype
     */
    public FieldDatatype getDatatype() {
        return datatype;
    }


    /**
     * Sets the minimum length of this form item.
     * 
     * @param value the minimum length
     */
    public void setMinLength(int value) {
        this.minLength = value;
    }


    /**
     * Gets the minimum length of this form item.
     * 
     * @return the minimum length
     */
    public int getMinLength() {
        return minLength;
    }


    /**
     * Sets the maximum length of this form item.
     * 
     * @param value the maximum length
     */
    public void setMaxLength(int value) {
        this.maxLength = value;
    }


    /**
     * Gets the maximum length of this form item.
     * 
     * @return the maximum length
     */
    public int getMaxLength() {
        return maxLength;
    }


    /**
     * Sets the minimum value permitted for this item (number fields only).
     * 
     * @param value the minimum value
     */
    public void setMinValue(Number value) {
        this.minValue = value;
    }

    
    /**
     * Gets the minimum value of the number field.
     * 
     * @return the minimum value
     */
    public Number getMinValue() {
        return minValue;
    }


    /**
     * Sets the maximum value permitted for this item (number fields only).
     * 
     * @param value the maximum value
     */
    public void setMaxValue(Number value) {
        this.maxValue = value;
    }


    /**
     * Gets the maximum value of the number field.
     * 
     * @return the maximum value
     */
    public Number getMaxValue() {
        return maxValue;
    }


    /**
     * Sets the default value of this field.
     * 
     * @param value the default value
     */
    public void setDefaultValue(Object value) {
        this.defaultValue = value;
    }


    /**
     * Gets the default value.
     * 
     * @return the default value
     */
    public Object getDefaultValue() {
        return defaultValue;
    }


    /**
     * Sets enumeration of possible values.
     * 
     * @param values possible values
     */
    public void setPossibleValues(Object[] values) {
        this.possibleValues = values;
    }

    
    /**
     * Gets the enumeration of possible values.
     * 
     * @return possible values
     */
    public Object[] getPossibleValues() {
        return possibleValues;
    }



    
    public Object getValue() {
        return value;
    }



    
    public void setValue(Object value) {
        this.value = value;
    }


}
