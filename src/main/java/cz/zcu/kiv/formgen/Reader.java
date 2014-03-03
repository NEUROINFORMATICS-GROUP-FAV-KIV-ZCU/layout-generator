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
 * Reader.java, 1. 3. 2014 18:25:56 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen;

import java.io.InputStream;
import cz.zcu.kiv.formgen.model.Form;


/**
 * Reader of the model from a transport serialization format.
 *
 * @author Jakub Krauz
 */
public interface Reader {
    
    
    /**
     * Reads the serialization from the specified input stream and
     * returns the internal model.
     * 
     * @param stream - input stream with the serialization
     * @return internal model
     * @throws LayoutGeneratorException if an error occured while reading the serialization
     */
    Form read(InputStream stream) throws LayoutGeneratorException;

}
