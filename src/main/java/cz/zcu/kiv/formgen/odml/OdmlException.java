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
 * OdmlException.java, 2. 3. 2014 19:39:06 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml;

import cz.zcu.kiv.formgen.TemplateGeneratorException;


/**
 * Indicates an error in the odML module.
 *
 * @author Jakub Krauz
 */
public class OdmlException extends TemplateGeneratorException {
    
    /** Serial version UID. */
    private static final long serialVersionUID = 3033277123646906874L;


    /**
     * Constructs a new exception with the given detail messsage.
     * @param message the detail message
     */
    public OdmlException(String message) {
        super(message);
    }
    
    
    /**
     * Constructs a new exception with the given detail message and cause.
     * @param message the detail message
     * @param cause the cause
     */
    public OdmlException(String message, Throwable cause) {
        super(message, cause);
    }

}
