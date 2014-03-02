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
 * BadItemTypeException.java, 2. 3. 2014 12:58:18 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;


/**
 * Exception thrown if the actual item type does not match the expected type.
 * 
 * @author Jakub Krauz
 */
public class BadItemTypeException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    
    /**
     * Constructor.
     * 
     * @param expected - the expected item type
     * @param actual - the actual item type
     */
    public BadItemTypeException(Type expected, Type actual) {
        super("FormItem of bad type inserted in the FormSet. Expected " + expected.getValue() 
                + ", got " + actual.getValue() + ".");
    }
    

}
