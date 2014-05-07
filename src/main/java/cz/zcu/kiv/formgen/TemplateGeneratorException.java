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
 * TemplateGeneratorException.java, 2. 3. 2014 20:54:16 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen;


/**
 * The root exception thrown by the layout-generator tool.
 * All exceptions used by this tool are descendants of this one.
 *
 * @author Jakub Krauz
 */
public class TemplateGeneratorException extends Exception {
    
    /** The serial version UID (default value). */
    protected static final long serialVersionUID = 1L;


    /**
     * Creates a new exception with the specified detail message.
     * 
     * @param message The detail message
     */
    public TemplateGeneratorException(String message) {
        super(message);
    }
    
    
    /**
     * Creates a new exception with the specified detail message and cause.
     * 
     * @param message The detail message.
     * @param cause The cause.
     */
    public TemplateGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }

}
