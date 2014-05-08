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
 * SimpleDataGenerator.java, 2. 4. 2014 11:53:34 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import cz.zcu.kiv.formgen.DataGenerator;
import cz.zcu.kiv.formgen.model.FormData;


/**
 * Converts data objects to the internal {@link FormData} model.
 *
 * @author Jakub Krauz
 */
public class SimpleDataGenerator implements DataGenerator {
    
    /** Data parser object. */
    private DataParser parser = new DataParser();
    
    /** Set of loaded data. */
    private Set<FormData> loadedData = new HashSet<FormData>();
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public FormData load(Object dataEntity) {
        return load(dataEntity, true);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<FormData> load(Collection<Object> data) {
        return load(data, true);
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public FormData load(Object dataEntity, boolean includeReferences) {
        FormData data =  parser.parse(dataEntity, includeReferences);
        loadedData.add(data);
        return data;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<FormData> load(Collection<Object> data, boolean includeReferences) {
        Collection<FormData> model = new HashSet<FormData>(data.size());
        
        for (Object obj : data)
            model.add(load(obj, includeReferences));
        
        return model;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<FormData> getLoadedModel() {
        return loadedData;
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void clearModel() {
        loadedData.clear();
    }
   

}
