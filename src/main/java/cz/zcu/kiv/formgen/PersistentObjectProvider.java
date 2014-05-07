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
 * PersistentObjectProvider.java, 29. 4. 2014 15:09:40 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen;


/**
 * Retrieves persistent objects.
 *
 * @author Jakub Krauz
 */
public interface PersistentObjectProvider<PK> {
    
    /**
     * Retrieves a persistent object by its ID.
     * 
     * @param cls The runtime class of the persistent object.
     * @param id The ID of the persistent object.
     * @return The required persistent object.
     * @throws PersistentObjectException If the required persistent object cannot be retrieved.
     */
    Object getById(Class<?> cls, PK id) throws PersistentObjectException;

}
