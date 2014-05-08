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
 * FormDataItem.java, 2. 4. 2014 10:47:38 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;


/**
 * Interface for all form data items.
 * 
 * <p>
 * A form data item represents a data record related to a form. It can be either
 * a form, subform or an individual field.
 * </p>
 * 
 * @author Jakub Krauz
 */
public interface FormDataItem {
    
    /**
     * Sets the type of the data item.
     * 
     * @param type The type to be set.
     */
    void setType(String type);
    
    /**
     * Returns the type of the data item.
     * 
     * @return The type.
     */
    String getType();
    
    /**
     * Sets the name of the data item.
     * 
     * @param name The name to be set.
     */
    void setName(String name);
    
    /**
     * Returns the name of the data item.
     * 
     * @return The name.
     */
    String getName();
    
    /**
     * Returns true if this data item represents a simple type (i.e. value of a form field),
     * otherwise returns false.
     * 
     * @return True if this item represents a simple type, false otherwise.
     */
    boolean isSimpleType();

}
