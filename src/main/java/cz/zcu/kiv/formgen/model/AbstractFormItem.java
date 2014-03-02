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
 * AbstractFormItem.java, 28. 2. 2014 16:57:30 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;


/**
 * Abstract implementation of {@link FormItem} interface.
 *
 * @author Jakub Krauz
 */
public abstract class AbstractFormItem implements FormItem {
    
    /** Type of the form item. */
    protected Type type;
    
    /** ID of the form item. */
    protected int id;
    
    /** Name of the form item. */
    protected String name;
    
    /** Label of the form item. */
    protected String label;
    
    /** Required flag of the form item. */
    protected boolean required;
    
    
    
    /**
     * Constructor.
     * Sets the name and type of the form item.
     * 
     * @param name - the name of the form item
     * @param type - the type of the form item
     */
    public AbstractFormItem(String name, Type type) {
        this.name = name;
        this.type = type;
    }
    
    
    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.model.FormItem#getType()
     */
    @Override
    public Type getType() {
        return type;
    }


    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.model.FormItem#setType(cz.zcu.kiv.formgen.model.Type)
     */
    @Override
    public void setType(Type type) {
        this.type = type;
    }
    

    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.model.FormItem#setId(int)
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }


    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.model.FormItem#getId()
     */
    @Override
    public int getId() {
        return id;
    }
    
    
    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.model.FormItem#setName(java.lang.String)
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }
    

    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.model.FormItem#getName()
     */
    @Override
    public String getName() {
        return name;
    }


    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.model.FormItem#setLabel(java.lang.String)
     */
    @Override
    public void setLabel(String label) {
        this.label = label;
    }


    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.model.FormItem#getLabel()
     */
    @Override
    public String getLabel() {
        return label;
    }


    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.model.FormItem#setRequired(boolean)
     */
    @Override
    public void setRequired(boolean required) {
        this.required = required;
    }


    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.model.FormItem#isRequired()
     */
    @Override
    public boolean isRequired() {
        return required;
    }

}
