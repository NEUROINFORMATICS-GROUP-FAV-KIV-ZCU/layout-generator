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
 * OdmlException.java, 2. 3. 2014 19:39:06 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml;

import cz.zcu.kiv.formgen.LayoutGeneratorException;


/**
 *
 * @author Jakub Krauz
 */
public class OdmlException extends LayoutGeneratorException {
    

    private static final long serialVersionUID = 1L;


    public OdmlException(String message) {
        super(message);
    }
    
    
    public OdmlException(String message, Throwable cause) {
        super(message, cause);
    }

}
