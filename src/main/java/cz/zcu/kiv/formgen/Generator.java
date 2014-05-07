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
 * Generator.java, 27. 2. 2014 12:18:01 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen;

import java.util.Collection;


/**
 * Enables to control the generator tool.
 * 
 * @author Jakub Krauz
 */
public interface Generator<T, U> {


    /**
     * Parses the given object and creates an appropriate model.
     * 
     * @param object The object to be parsed.
     * @return The created model.
     * @throws FormNotFoundException If the {@link cz.zcu.kiv.formgen.annotation.Form @Form}
     *          annotation was not found.
     */
    U load(T object) throws FormNotFoundException;


    /**
     * Parses the given collection of objects and creates an appropriate model.
     * 
     * @param objects The collection of objects to be parsed.
     * @return The created model.
     * @throws FormNotFoundException If no {@link cz.zcu.kiv.formgen.annotation.Form @Form}
     *          was found in any of parsed objects.
     */
    Collection<U> load(Collection<T> objects) throws FormNotFoundException;


    /**
     * Gets all model obejcts loaded since the construction of this object or since
     * the last call of {@link #clearModel()}.
     * 
     * @return The complete loaded model.
     */
    Collection<U> getLoadedModel();


    /**
     * Clear the loaded model.
     */
    void clearModel();

}
