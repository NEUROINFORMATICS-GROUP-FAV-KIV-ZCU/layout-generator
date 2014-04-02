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
 * SimpleFormDataGenerator.java, 2. 4. 2014 11:53:34 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import cz.zcu.kiv.formgen.FormNotFoundException;
import cz.zcu.kiv.formgen.model.FormData;


/**
 *
 * @author Jakub Krauz
 */
public class SimpleFormDataGenerator {
    
    private DataParser parser = new DataParser();
    
    private Set<FormData> data = new HashSet<FormData>();
    
    
    
    public void load(Object... objects) throws FormNotFoundException {
        for (Object obj : objects) {
            if (Utils.annotation(obj, cz.zcu.kiv.formgen.annotation.Form.class) != null)
                data.add(parser.parse(obj));
            /*else if (annotation(obj, cz.zcu.kiv.formgen.annotation.MultiForm.class) != null)
                load(obj, true);*/
            else
                throw new FormNotFoundException();
        }
    }
    
    
    public void loadObjects(Collection<Object> collection) throws FormNotFoundException {
        for (Object object : collection)
            load(object);
    }


    public Collection<FormData> getFormData() {
        return data;
    }
   

}
