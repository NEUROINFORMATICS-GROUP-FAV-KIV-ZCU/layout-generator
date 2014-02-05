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
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 ***********************************************************************************************************************
 *
 * OdmlForm.java, 1. 12. 2013 19:11:18 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml;

import odml.core.Section;
import cz.zcu.kiv.formgen.Form;
import cz.zcu.kiv.formgen.FormItem;


/**
 * Form in odML layout.
 *
 * @author Jakub Krauz
 */
public class OdmlForm extends Section implements Form {
    
    private static final long serialVersionUID = 1L;
    
    public static final String TYPE = SectionType.FORM.getValue();
    
    public OdmlForm(String name) throws Exception {
        super(name, TYPE);
        if (name == null)
            throw new Exception("Name must not me null.");
    }

    @Override
    public void addItem(FormItem item) {
        add((Section) item);
    }

    @Override
    public void setDescription(String description) {
        setDefinition(description);
    }
    
    public String getDescription() {
        return getDefinition();
    }

    @Override
    public void setId(int id) {
        addProperty("id", id);
    }

    @Override
    public int getId() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setLabel(String label) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getLabel() {
        // TODO Auto-generated method stub
        return null;
    }

}
