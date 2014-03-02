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
 * FormSet.java, 28. 2. 2014 17:23:34 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;

import java.util.Vector;


/**
 * Form set is a special type of form item which contains N form items (of the same type).
 * 
 * <p>
 * For example: Consider a form gathering information about a person. There can be items like
 * name, age, weight and so on. And what about car? One person can have several cars - it is exactly
 * the use-case for this class.
 * </p>
 *
 * @author Jakub Krauz
 */
public class FormSet extends AbstractFormItem implements FormItemContainer {
    
    /** Vector of form items. */
    private Vector<FormItem> items = new Vector<FormItem>();
    
    /** Type of contained items. */
    private Type innerType;
    
    
    
    /**
     * Constructor.
     * 
     * @param name - name of the form item
     * @param type - type of contained items
     */
    public FormSet(String name, Type type) {
        super(name, Type.SET);
        this.innerType = type;
    }
    

    @Override
    public void addItem(FormItem item) throws BadItemTypeException {
        if (innerType == null)
            innerType = item.getType();
        else if (item.getType() != innerType)
            throw new BadItemTypeException(innerType, item.getType());
        items.add(item);
    }


    @Override
    public Vector<FormItem> getItems() {
        return items;
    }

}
