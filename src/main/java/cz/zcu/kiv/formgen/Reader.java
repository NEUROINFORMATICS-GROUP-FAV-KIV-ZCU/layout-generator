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
 * Reader.java, 1. 3. 2014 18:25:56 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen;

import java.io.InputStream;
import java.util.Set;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormData;


/**
 * Reads an internal {@link Form} or {@link FormData} model from a transport format.
 *
 * @author Jakub Krauz
 */
public interface Reader {
    
    
    /**
     * Reads the form layout serialization from the specified input stream and
     * returns the internal {@link Form} model.
     * 
     * @param stream Input stream with the serialization.
     * @return The {@link Form} object.
     * @throws TemplateGeneratorException If an error occured while reading the serialization.
     */
    Form readLayout(InputStream stream) throws TemplateGeneratorException;
    
    
    /**
     * Reads the form data serialization from the specified input stream and
     * returns the internal {@link FormData} model.
     * 
     * @param stream Input stream with the serialization.
     * @return The corresponsing {@link FormData} objects.
     * @throws TemplateGeneratorException If an error occured while reading the serialization.
     */
    Set<FormData> readData(InputStream stream) throws TemplateGeneratorException;

}
