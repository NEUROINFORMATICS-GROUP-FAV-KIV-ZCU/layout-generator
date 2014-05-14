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
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 *********************************************************************************************************************** 
 * 
 * DataGenerator.java, 27. 2. 2014 12:22:55 Jakub Krauz
 * 
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen;

import java.util.Collection;
import cz.zcu.kiv.formgen.model.FormData;


/**
 * Converts data objects to the internal {@link FormData} model.
 * 
 * @author Jakub Krauz
 */
public interface DataGenerator extends Generator<Object, FormData> {
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    FormData load(Object dataEntity);

    
    /**
     * Converts the given data object to {@link FormData}. If the <code>includeReferences</code>
     * argument is true then all referenced data objects are parsed as well. Otherwise, only
     * refrences to their IDs are added to the model.
     * 
     * @param dataEntity The data object to be parsed.
     * @param includeReferences If true, all referenced objects are parsed as well.
     * @return The internal data model.
     */
    FormData load(Object dataEntity, boolean includeReferences);

    
    /**
     * {@inheritDoc}
     */
    @Override
    Collection<FormData> load(Collection<Object> data);

    
    /**
     * Converts the given collection of data objects to {@link FormData}.
     * If the <code>includeReferences</code> argument is true then all referenced data
     * objects are parsed as well. Otherwise, only refrences to their IDs are added to the model.
     * 
     * @param data The collection of data objects to be parsed.
     * @param includeReferences If true, all referenced objects are parsed as well.
     * @return The internal data model.
     */
    Collection<FormData> load(Collection<Object> data, boolean includeReferences);

}
