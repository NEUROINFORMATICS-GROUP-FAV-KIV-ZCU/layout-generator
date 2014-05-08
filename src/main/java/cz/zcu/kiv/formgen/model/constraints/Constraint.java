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
 * Constraint.java, 3. 4. 2014 18:10:45 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model.constraints;


/**
 * Common interface for all constraints.
 * 
 * @author Jakub Krauz
 */
public interface Constraint {
    
    /**
     * Returns name of the constraint (e.g. for serialization purposes).
     * @return the name
     */
    String name();
    
    /**
     * Returns value of the constraint (its meaning is determined by the concrete constraint type).
     * @return the value
     */
    Object value();

}
