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
 * Writer.java, 25. 11. 2013 18:53:13 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen;

import java.io.OutputStream;
import java.util.Collection;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormData;


/**
 * Enables serialization of the specified internal model to the output stream.
 * 
 * @author Jakub Krauz
 */
public interface Writer {
    
    
    /**
     * Writes the serialization of the internal model to the output stream.
     * 
     * @param form - the internal model
     * @param outputStream - the stream to which the model will be written
     */
    void writeLayout(Form form, OutputStream outputStream) throws LayoutGeneratorException;
    
    
    void writeLayout(Collection<Form> forms, OutputStream outputStream) throws LayoutGeneratorException;
    
    
    void writeData(FormData data, OutputStream out) throws LayoutGeneratorException;
    
    void writeData(Collection<FormData> data, OutputStream out) throws LayoutGeneratorException;
    
}
