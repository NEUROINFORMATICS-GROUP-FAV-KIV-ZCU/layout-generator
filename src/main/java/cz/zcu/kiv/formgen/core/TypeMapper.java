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
 * TypeMapper.java, 16. 12. 2013 13:34:34 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;


/**
 * This class is used to map types from Java to those used in form layouts.
 *
 * @author Jakub Krauz
 */
public interface TypeMapper {
    
    /**
     * Determines whether given Java type is considered a simple type in the terms
     * of form layout.
     * 
     * @param type the Java type
     * @return true if the given type is considered simple, false otherwise
     */
    boolean isSimpleType(Class<?> type);
    
    
    /**
     * Maps the given Java type to an appropriate type used in form layouts.
     * @param type the Java type
     * @return type name used in form layout
     */
    String mapType(Class<?> type);

}
