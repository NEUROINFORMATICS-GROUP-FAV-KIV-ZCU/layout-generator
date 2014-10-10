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
 * Converter.java, 10. 10. 2014 16:46:26 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml;

import java.util.Collection;
import java.util.Set;

import odml.core.Section;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormData;


/**
 * Converts between internal model and corresponding odML tree.
 *
 * @author Jakub Krauz
 */
public interface Converter {
    
    
    /**
     * Converts the internal {@link Form} model to odML section tree.
     * 
     * @param form The internal model to be converted.
     * @return corresponding odML section tree
     * @throws OdmlConvertException If an error occurs during the conversion.
     */
    Section layoutToOdml(Form form) throws OdmlConvertException;
    
    
    /**
     * Converts the internal {@link Form} model to odML section tree.
     * 
     * @param forms The internal model to be converted.
     * @return corresponding odML tree
     * @throws OdmlConvertException If an error occurs during the conversion.
     */
    Section layoutToOdml(Collection<Form> forms) throws OdmlConvertException;
    
    
    /**
     * Converts the internal {@link FormData} model to odML section tree.
     * 
     * @param data The internal model to be converted.
     * @return corresponding odML tree
     * @throws OdmlConvertException If an error occurs during the conversion.
     */
    Section dataToOdml(FormData data) throws OdmlConvertException;
    
    
    /**
     * Converts the internal {@link FormData} model to odML section tree.
     * 
     * @param data The internal model to be converted.
     * @return corresponding odML tree
     * @throws OdmlConvertException If an error occurs during the conversion.
     */
    Section dataToOdml(Collection<FormData> data) throws OdmlConvertException;
    
    
    /**
     * Converts odML section tree to internal {@link Form} model.
     * 
     * @param section The root section of the odML tree.
     * @return corresponding form-layout model
     * @throws OdmlConvertException If an error occurs during the conversion.
     */
    Form odmlToLayoutModel(Section section) throws OdmlConvertException;
    
    
    /**
     * Converts odML section tree to internal {@link FormData} model.
     *  
     * @param odmlRoot The root section of odML tree.
     * @return corresponding form-data model
     * @throws OdmlConvertException If an error occurs during the conversion.
     */
    Set<FormData> odmlToDataModel(Section odmlRoot) throws OdmlConvertException;
    
}
