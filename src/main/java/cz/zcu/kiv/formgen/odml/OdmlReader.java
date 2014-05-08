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
 * OdmlReader.java, 1. 3. 2014 18:26:58 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml;

import java.io.InputStream;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import odml.core.Section;
import cz.zcu.kiv.formgen.TemplateGeneratorException;
import cz.zcu.kiv.formgen.Reader;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormData;


/**
 * The odML implementation of the {@link Reader} interface.
 * 
 * <p>
 * OdmlReader provides methods that enable reading {@link Form} or {@link FormData} models
 * from their odML serialization format.
 * </p>
 *
 * @author Jakub Krauz
 */
public class OdmlReader implements Reader {
    
    /** Logger. */
    final Logger logger = LoggerFactory.getLogger(OdmlReader.class);

    
    /**
     * {@inheritDoc} The serialization must be in the odML format, otherwise
     * {@link OdmlException} is thrown.
     */
    @Override
    public Form readLayout(InputStream stream) throws OdmlException {
        odml.core.Reader reader = new odml.core.Reader();
        Section root = null;
        
        try {
            root = reader.load(stream);
        } catch (Exception e) {
            logger.error("Unable to load the odML document!", e);
            throw new OdmlException("Unable to load the odML document.", e);
        }
        
        Converter converter = new Converter();
        Section formSection = root.getSection(0);
        if (formSection == null)
            throw new OdmlException("The odML document does not contain any form.");
        
        return converter.odmlToLayoutModel(formSection);
    }


    /**
     * {@inheritDoc} The serialization must be in the odML format, otherwise
     * {@link OdmlException} is thrown.
     */
    @Override
    public Set<FormData> readData(InputStream stream) throws TemplateGeneratorException {
        odml.core.Reader reader = new odml.core.Reader();
        Section root = null;
        
        try {
            root = reader.load(stream);
        } catch (Exception e) {
            logger.error("Unable to load the odML document!", e);
            throw new OdmlException("Unable to load the odML document.", e);
        }
        
        Converter converter = new Converter();
        return converter.odmlToDataModel(root);
    }

}
