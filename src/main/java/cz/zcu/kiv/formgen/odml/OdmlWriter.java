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
 * OdmlWriter.java, 25. 11. 2013 19:01:38 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml;

import java.io.OutputStream;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import odml.core.Section;
import cz.zcu.kiv.formgen.Writer;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormData;


/**
 * The odML implementation of the {@link Writer} interface.
 * 
 * <p>
 * OdmlWriter provides means to write {@link Form} or {@link FormData} models
 * to an output stream using the odML serialization format.
 * </p> 
 * 
 * @author Jakub Krauz
 */
public class OdmlWriter implements Writer {
    
    /** Logger. */
    final Logger logger = LoggerFactory.getLogger(OdmlWriter.class);
    
    /** The style of odml templates. */
    private final TemplateStyle style;
   
    /** The converter between odML and internal model. */
	private Converter converter;
	
	
	/**
	 * Creates a new OdmlWriter, which provides the ability to write
	 * model objects to a stream using required style (see {@link TemplateStyle}.
	 * 
	 * @param style The style of odml templates.
	 */
	public OdmlWriter(TemplateStyle style) {
		this.style = style;
        switch (style) {
            case EEGBASE:
                converter = new OdmlConverter();
                break;
            case GUI_NAMESPACE:
                converter = new OdmlGuiConverter();
                break;
            default:
                logger.error("Unknown odml-template style required.");
                converter = new OdmlConverter();
        }
    }

    
    /**
     * {@inheritDoc}
     */
    @Override
    public void writeLayout(Form form, OutputStream outputStream) throws OdmlException {
        if (form == null)
            return;
        if (outputStream == null)
            throw new NullPointerException("Output stream must not be null.");
        
        try {
            Section root = new Section();
            root.add(converter.layoutToOdml(form));
            //odml.core.Writer writer = new odml.core.Writer(root);  // does not write empty properties
            odml.core.Writer writer = new odml.core.Writer(root, true, 
            		(style == TemplateStyle.GUI_NAMESPACE));
            writer.write(outputStream);
        } catch (OdmlConvertException e) {
            throw new OdmlException("Could not convert the internal model to odML.", e);
        }
    }

    
    /**
     * {@inheritDoc}
     */
    @Override
    public void writeLayout(Collection<Form> forms, OutputStream outputStream) throws OdmlException {
        if (forms == null)
            return;
        if (outputStream == null)
            throw new NullPointerException("Output stream must not be null.");
        
        try {
            Section root = converter.layoutToOdml(forms);
            //odml.core.Writer writer = new odml.core.Writer(root);  // does not write empty properties
            odml.core.Writer writer = new odml.core.Writer(root, true, 
            		(style == TemplateStyle.GUI_NAMESPACE));
            writer.write(outputStream);
        } catch (OdmlConvertException e) {
            throw new OdmlException("Could not convert the internal model to odML.", e);
        }
    }

    
    /**
     * {@inheritDoc}
     */
    @Override
    public void writeData(FormData data, OutputStream outputStream) throws OdmlException {
        if (data == null)
            return;
        if (outputStream == null)
            throw new NullPointerException("Output stream must not be null.");
        
        try {
            Section root = new Section();
            root.add(converter.dataToOdml(data));
            //odml.core.Writer writer = new odml.core.Writer(root);  // does not write empty properties
            odml.core.Writer writer = new odml.core.Writer(root, false, 
            		(style == TemplateStyle.GUI_NAMESPACE));
            writer.write(outputStream);
        } catch (OdmlConvertException e) {
            throw new OdmlException("Could not convert the internal model to odML.", e);
        }
    }

    
    /**
     * {@inheritDoc}
     */
    @Override
    public void writeData(Collection<FormData> data, OutputStream outputStream) throws OdmlException {
        if (data == null)
            return;
        if (outputStream == null)
            throw new NullPointerException("Output stream must not be null.");
        
        try {
            Section root = converter.dataToOdml(data);
            //odml.core.Writer writer = new odml.core.Writer(root);  // does not write empty properties
            odml.core.Writer writer = new odml.core.Writer(root, false, 
            		(style == TemplateStyle.GUI_NAMESPACE));
            writer.write(outputStream);
        } catch (OdmlConvertException e) {
            throw new OdmlException("Could not convert the internal model to odML.", e);
        }
    }
    

}
