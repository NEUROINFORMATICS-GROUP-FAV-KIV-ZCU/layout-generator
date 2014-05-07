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
 * PersistentObjectException.java, 29. 4. 2014 15:16:33 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen;


/**
 * Exception thrown by {@link PersistentObjectProvider} if the required persistent
 * object cannot be obtained.
 * 
 * @author Jakub Krauz
 */
public class PersistentObjectException extends TemplateGeneratorException {

    /** Serial version UID. */
    private static final long serialVersionUID = 6137436728780251071L;


    /**
     * Creates a new exception with the specified detail message.
     * 
     * @param message The detail message.
     */
    public PersistentObjectException(String message) {
        super(message);
    }


    /**
     * Creates a new exception with the specified detail message and cause.
     * 
     * @param message The detail message.
     * @param cause The cause.
     */
    public PersistentObjectException(String message, Throwable cause) {
        super(message, cause);
    }

}
