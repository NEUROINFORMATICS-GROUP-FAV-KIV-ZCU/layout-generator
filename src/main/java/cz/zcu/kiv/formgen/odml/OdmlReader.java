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
 * OdmlReader.java, 1. 3. 2014 18:26:58 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml;

import java.io.InputStream;
import odml.core.Section;
import cz.zcu.kiv.formgen.Reader;
import cz.zcu.kiv.formgen.model.Form;


/**
 *
 * @author Jakub Krauz
 */
public class OdmlReader implements Reader {

    
    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.Reader#read(java.io.InputStream)
     */
    @Override
    public Form read(InputStream stream) {
        Form form = null;
        
        try {
            odml.core.Reader reader = new odml.core.Reader();
            Section section = reader.load(stream);
            Converter converter = new Converter();
            form = converter.odmlToForm(section);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return form;
    }

}
