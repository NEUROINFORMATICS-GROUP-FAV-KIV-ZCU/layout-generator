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
 * FormNotFoundException.java, 15. 11. 2013 18:58:18 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen;


/**
 * This exception indicates that the required {@link cz.zcu.kiv.formgen.annotation.Form @Form}
 * annotation was not found.
 *
 * @author Jakub Krauz
 */
public class FormNotFoundException extends LayoutGeneratorException {

    /** The serial version UID (default value). */
    private static final long serialVersionUID = 1L;

    
    /**
     * Constructor.
     * Creates a new exception.
     */
    public FormNotFoundException() {
        super("The @Form annotation was not found in the given class(es).");
    }

}
