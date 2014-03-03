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
 * FormField.java, 1. 12. 2013 19:10:07 Jakub Krauz
 * 
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;

import java.util.Arrays;


/**
 * Represents a form field.
 * 
 * <p>
 * Form field is a special type of form item that can be set a simple value. Simple means
 * for example text, number, date, boolean etc. The value is not structured, i.e. that the
 * GUI shows a single input box or another widget for the value input. Structured items
 * can be represented by subforms (see {@link Form}).
 * </p>
 * 
 * @author Jakub Krauz
 */
public class FormField extends AbstractFormItem implements FormItem {
    
    /** Maximal count of combobox values. If the number of values is higher,
     * choice (list) is used instead. */
    private static final int COMBOBOX_MAX_ITEMS = 5;
    
    /** Datatype of the input value. */
    private FieldDatatype datatype;
    
    /** Minimal length of the input value. */
    private int minLength = -1;
    
    /** Maximal length of the input value. */
    private int maxLength = -1;
    
    /** Minimal value (numeric datatypes only). */
    private Number minValue;
    
    /** Maximal value (numeric datatypes only). */
    private Number maxValue;
    
    /** Default input value. */
    private Object defaultValue;
    
    /** Set of possible values. */
    private Object possibleValues[];
    
    /** The input value. */
    private Object value;
    
    
    
    /*@Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (! (other instanceof FormField))
            return false;
        
        FormField f = (FormField) other;
        return name.equals(f.name) && type == f.type && datatype == f.datatype;
    }*/


    /**
     * Constructor.
     * Sets the name of the form field.
     * 
     * @param name - the name of the form field
     */
    public FormField(String name) {
        this(name, null, null);
    }
    
    
    /**
     * Constructor.
     * Sets the name, type and datatype.
     * 
     * @param name - the name of the form field
     * @param type - the type of the form field
     * @param datatype - the datatype of the input value
     */
    public FormField(String name, Type type, FieldDatatype datatype) {
        super(name, type);
        this.datatype = datatype;
    }


    /**
     * Sets the datatype of the input value.
     * 
     * @param datatype - the datatype
     */
    public void setDatatype(FieldDatatype datatype) {
        this.datatype = datatype;
    }


    /**
     * Gets the datatype of the input value.
     * 
     * @return the datatype of the input value
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
        if (values.length <= COMBOBOX_MAX_ITEMS)
            this.type = Type.COMBOBOX;
        else
            this.type = Type.CHOICE;
    }

    
    /**
     * Gets the enumeration of possible values.
     * 
     * @return possible values
     */
    public Object[] getPossibleValues() {
        return possibleValues;
    }


    /**
     * Gets the input value.
     * 
     * @return the input value (null if not set)
     */
    public Object getValue() {
        return value;
    }


    /**
     * Sets the input value.
     * 
     * @param value - the input value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    
    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((datatype == null) ? 0 : datatype.hashCode());
        result = prime * result + ((defaultValue == null) ? 0 : defaultValue.hashCode());
        result = prime * result + maxLength;
        result = prime * result + ((maxValue == null) ? 0 : maxValue.hashCode());
        result = prime * result + minLength;
        result = prime * result + ((minValue == null) ? 0 : minValue.hashCode());
        result = prime * result + Arrays.hashCode(possibleValues);
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }


    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (!super.equals(obj)) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        FormField other = (FormField) obj;
        if (datatype != other.datatype) { return false; }
        if (defaultValue == null) {
            if (other.defaultValue != null) { return false; }
        } else if (!defaultValue.equals(other.defaultValue)) { return false; }
        if (maxLength != other.maxLength) { return false; }
        if (maxValue == null) {
            if (other.maxValue != null) { return false; }
        } else if (!maxValue.equals(other.maxValue)) { return false; }
        if (minLength != other.minLength) { return false; }
        if (minValue == null) {
            if (other.minValue != null) { return false; }
        } else if (!minValue.equals(other.minValue)) { return false; }
        if (!Arrays.equals(possibleValues, other.possibleValues)) { return false; }
        if (value == null) {
            if (other.value != null) { return false; }
        } else if (!value.equals(other.value)) { return false; }
        return true;
    }

    

}
