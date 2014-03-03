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
 * FormDataGenerator.java, 27. 2. 2014 12:22:55 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen;

import java.util.Collection;


/**
 * Interface that enables to control the form-data-generator tool.
 *
 * @author Jakub Krauz
 */
public interface FormDataGenerator extends Generator<Object> {
    
    
    /**
     * Parses all objects in the collection and creates an appropriate model.
     * 
     * @param collection - the collection of objects to be parsed
     * @throws FormNotFoundException if no {@link cz.zcu.kiv.formgen.annotation.Form @Form} was found in any of parsed objects
     */
    void loadObjects(Collection<Object> collection) throws FormNotFoundException;

}
