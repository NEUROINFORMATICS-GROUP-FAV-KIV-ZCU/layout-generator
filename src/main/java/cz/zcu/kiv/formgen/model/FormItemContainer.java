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
 * FormItemContainer.java, 23. 1. 2014 14:42:46 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;

import java.util.Vector;


/**
 * Interface of a container of form items.
 * 
 * <p>
 * Form item container represents a special type of form item - a collection of form fields and subforms.
 * It means that in the form there can be added 0 - n fields/subform of the same type in this item.
 * </p>
 *
 * @author Jakub Krauz
 */
public interface FormItemContainer extends FormItem {
    
    
    /**
     * Adds the given item to the end of this form container.
     * 
     * @param item - the item to be added
     */
    void addItem(FormItem item);
    
    
    /**
     * Returns item on the given position (indexed from 0) or null.
     * 
     * @param index - the index of the position
     * @return item on the specified position or null
     */
    FormItem getItemAt(int position);
    
    
    /**
     * Gets all items in the container.
     * 
     * @return vector of contained items
     */
    Vector<FormItem> getItems();
    
    
    /**
     * Returns the number of contained items.
     * 
     * @return number of items
     */
    int countItems();
    

}
