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
 * PersistentObjectBuilder.java, 29. 4. 2014 15:45:05 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import cz.zcu.kiv.formgen.PersistentObjectProvider;
import cz.zcu.kiv.formgen.model.FormData;


/**
 *
 * @author Jakub Krauz
 */
public class PersistentObjectBuilder<PK> extends ObjectBuilder {
    
    private PersistentObjectProvider<PK> provider;

    
    /**
     * 
     */
    public PersistentObjectBuilder(PersistentObjectProvider<PK> provider) {
        this.provider = provider;
    }
    
    
    
    @Override
    protected Object createInstance(Class<?> type, FormData data) throws ObjectBuilderException {
        if (data.getId() != null) {
            try {
                return provider.getById(type, (PK) data.getId());
            } catch (Exception e) {
                final String message = "Cannot get persistent object " + type.getName();
                logger.error(message, e);
                throw new ObjectBuilderException(message, e);
            }
        } else {
            return super.createInstance(type, data);
        }
    }

}
