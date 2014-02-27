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


/**
 *
 * @author Jakub Krauz
 */
public interface FormItem {
    
    
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

}
