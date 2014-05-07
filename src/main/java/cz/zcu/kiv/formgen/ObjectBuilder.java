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
 * ObjectBuilder.java, 29. 4. 2014 16:57:13 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen;

import cz.zcu.kiv.formgen.model.FormData;


/**
 * Provides the facility to build data objects from a corresponding {@link FormData} model.
 * 
 * @author Jakub Krauz
 */
public interface ObjectBuilder {

    
    /**
     * Builds a data object of the given <code>type</code> from the given {@link FormData} object.
     * 
     * @param data The FormData model object.
     * @param type The runtime class of the data object to be built.
     * @return The built data object.
     * @throws ObjectBuilderException If the data object cannot be built.
     */
    Object build(FormData data, Class<?> type) throws ObjectBuilderException;


    /**
     * Builds a data object of the given <code>type</code> from the given {@link FormData} object.
     * <p>
     * This method is similar to {@link #build(FormData, Class)} except this one returns the
     * data object typed to its instantiating class instead of the java.lang.Object class. This
     * requires the class to be known at compile time.
     * </p>
     * 
     * @param data The FormData model object.
     * @param type The runtime class of the data object to be built.
     * @return The built data object.
     * @throws ObjectBuilderException If the data object cannot be built.
     */
    <T> T buildTyped(FormData data, Class<T> type) throws ObjectBuilderException;

}
