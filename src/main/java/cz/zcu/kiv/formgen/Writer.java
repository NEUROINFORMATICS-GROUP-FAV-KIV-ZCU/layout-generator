/***********************************************************************************************************************
 *
 * This file is part of the layout-generator project
 *
 * =================================================
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
 * Writer.java, 25. 11. 2013 18:53:13 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen;

import java.io.OutputStream;
import java.util.Collection;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormData;


/**
 * Writes a serialization of the {@link Form} or {@link FormData} model to an output stream.
 * 
 * @author Jakub Krauz
 */
public interface Writer {
    
    
    /**
     * Writes the serialization of {@link Form} to the output stream.
     * 
     * @param form The {@link Form} model object.
     * @param outputStream The output stream to which the serialization will be written.
     * @throws TemplateGeneratorException If the serialization cannot be written to the output stream.
     */
    void writeLayout(Form form, OutputStream outputStream) throws TemplateGeneratorException;
    
    
    /**
     * Writes the serialization of a collection of {@link Form} objects to the output stream.
     * 
     * @param forms Collection of {@link Form} objects.
     * @param outputStream The output stream to which the serialization will be written.
     * @throws TemplateGeneratorException If the serialization cannot be written to the output stream.
     */
    void writeLayout(Collection<Form> forms, OutputStream outputStream) throws TemplateGeneratorException;
    
    
    /**
     * Writes the serialization of {@link FormData} to the output stream.
     * 
     * @param data The {@link FormData} model object.
     * @param outputSteam The output stream to which the serialization will be written.
     * @throws TemplateGeneratorException If the serialization cannot be written to the output stream.
     */
    void writeData(FormData data, OutputStream outputSteam) throws TemplateGeneratorException;
    
    
    /**
     * Writes the serialization of a collection of {@link FormData} objects to the output stream.
     * 
     * @param data The collection of {@link FormData} model objects.
     * @param outputSteam The output stream to which the serialization will be written.
     * @throws TemplateGeneratorException If the serialization cannot be written to the output stream.
     */
    void writeData(Collection<FormData> data, OutputStream outputStream) throws TemplateGeneratorException;
    
}
