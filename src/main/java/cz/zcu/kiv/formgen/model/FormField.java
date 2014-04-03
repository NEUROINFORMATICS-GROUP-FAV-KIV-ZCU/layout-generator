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

import java.util.HashSet;
import java.util.Set;
import cz.zcu.kiv.formgen.model.constraints.Constraint;
import cz.zcu.kiv.formgen.model.constraints.PossibleValues;


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
    
    /** Set of constraints for this field. */
    private Set<Constraint> constraints = new HashSet<Constraint>();
    


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
    
    
    public void addConstraint(Constraint constraint) {
        if (constraint != null)
            constraints.add(constraint);
        
        if (constraint instanceof PossibleValues) {
            if (((PossibleValues) constraint).getValues().length <= COMBOBOX_MAX_ITEMS)
                this.type = Type.COMBOBOX;
            else
                this.type = Type.CHOICE;
        }
    }
    
    
    public Set<Constraint> getConstraints() {
        return constraints;
    }

    
    

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((constraints == null) ? 0 : constraints.hashCode());
        result = prime * result + ((datatype == null) ? 0 : datatype.hashCode());
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
        FormField other = (FormField) obj;
        if (constraints == null) {
            if (other.constraints != null) { return false; }
        } else if (!constraints.equals(other.constraints)) { return false; }
        if (datatype != other.datatype) { return false; }
        return true;
    }

    

}
