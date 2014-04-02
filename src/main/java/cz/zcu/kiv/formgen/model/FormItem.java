/***********************************************************************************************************************
 *
 * This file is part of the layout-generator project
 *
 * ==========================================
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
 * FormItem.java, 5. 2. 2014 17:57:38 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;

import cz.zcu.kiv.formgen.model.constraints.Cardinality;


/**
 * Interface for objects that can be added to a form as its items.
 * 
 * <p>
 * Every {@link Form} consists of items which can be either fields or subforms.
 * Every item can be set 4 values - ID, name, label and required-flag. ID is a numeric identifier of the item
 * that can be used to refer to other items. Name is a unique name of the item.
 * Label is a text that should appear in the form right in front of the
 * item that allows user to set the value of the item.
 * If the required-flag is true then the item must be set in every form (cannot be left blank).
 * </p>
 *
 * @author Jakub Krauz
 */
public interface FormItem {
    
    
    /**
     * Gets the type of the form item.
     * 
     * @return the type of the form item
     */
    Type getType();


    /**
     * Sets the type of the form item.
     * 
     * @param type - the type to be set
     */
    void setType(Type type);
    
    
    /**
     * Sets the form item ID.
     * 
     * @param id - the ID to be set
     */
    void setId(int id);
    
    
    /**
     * Gets the ID of the form item.
     * 
     * @return the ID
     */
    int getId();
    
    
    /**
     * Sets the name of this item.
     * 
     * @param name the name
     */
    public void setName(String name);
    

    /**
     * Returns the name of this item.
     * 
     * @return the name
     */
    public String getName();
    
    
    /**
     * Sets the label to this form item.
     * 
     * @param label - label to be set
     */
    void setLabel(String label);
    
    
    /**
     * Gets the label of this form item.
     * 
     * @return the label
     */
    String getLabel();
    
    
    /**
     * Sets the required-flag for this form item.
     * 
     * @param required - value to be assigned to the requierd-flag
     */
    void setRequired(boolean required);


    /**
     * Returns the value of the required-flag.
     * 
     * @return the required-flag
     */
    boolean isRequired();
    
    
    void setCardinality(Cardinality cardinality);
    
    
    Cardinality getCardinality();

}
