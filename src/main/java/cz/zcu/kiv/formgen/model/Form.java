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
 * Form.java, 1. 12. 2013 19:07:32 Jakub Krauz
 * 
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;


/**
 * Represents a form.
 * 
 * <p>
 * Form consists of items (type {@link FormField}) and subforms of the same type. Items and subforms can be added to the
 * form using {@link #addItem(FormField)} or {@link #addSubform(Form)} respectively. Every form has its name, which
 * should be assigned during construction. A form can be assigned a description which is not compulsory.
 * </p>
 * 
 * @author Jakub Krauz
 */
public interface Form extends FormItem, FormItemContainer {
    
    
    /**
     * Returns the highest ID of contained form items.
     * 
     * @return the highest ID
     */
    int highestItemId();

    
    /**
     * Sets the name of this form.
     * 
     * @param name the name
     */
    void setFormName(String name);
    

    /**
     * Returns the name of this form.
     * 
     * @return the name
     */
    String getFormName();


    /**
     * Sets the given description to this form.
     * 
     * @param description - the description for this form
     */
    void setDescription(String description);
    
    
    /**
     * Gets the form's description.
     * 
     * @return the description
     */
    String getDescription();
    
    
    /**
     * Sets the name of the form layout.
     * 
     * @param name the name of the layout
     */
    void setLayoutName(String name);
    
    
    /**
     * Gets the name of the form layout.
     * 
     * @return the name of the form layout
     */
    String getLayoutName();

}
