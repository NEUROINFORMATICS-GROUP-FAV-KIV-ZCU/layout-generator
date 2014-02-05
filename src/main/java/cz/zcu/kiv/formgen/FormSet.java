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
 * FormSet.java, 23. 1. 2014 14:42:46 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen;


/**
 * Represents a set of form items or subforms.
 * 
 * <p>
 * Every {@link Form} consists of items and/or subforms. An item can be set a label and a required-flag.
 * Label is a text that should appear in the form right in front of the field
 * that allows user to set the value of the item. If the required-flag is true then
 * the item must be set in every form (cannot be left blank).
 * </p>
 * 
 * <p>
 * This class represents a special type of form item - a collection of items or subforms. It means
 * that in the form there can be added 0 - n items/subform of the same type in this item.
 * For example: Consider a form gathering information about a person. There can be items like
 * name, age, weight and so on. And what about car? One person can have several cars - it is exactly
 * the use-case for this class.
 * </p>
 *
 * @author Jakub Krauz
 */
public interface FormSet extends FormItem {
    
    
    /**
     * Sets the type of this collection's items to the given subform.
     * 
     * @param form the subform contained in the colection
     */
    void setContent(Form form);
    
    
    /**
     *  Sets the type of this collection's items to the given form item.
     *  
     * @param item the item contained in the collection
     */
    void setContent(FormItem item);
    

}
