/***********************************************************************************************************************
 *
 * This file is part of the odml-form-generator project
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
 * FormGeneratorImpl.java, 15. 11. 2013 17:36:16 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.impl;

import java.io.OutputStream;
import odml.core.Section;
import odml.core.Writer;
import cz.zcu.kiv.formgen.FormGenerator;


public class FormGeneratorImpl implements FormGenerator {
    
    private ClassParser parser = new ClassParser();
    
    private Section loadedForm;

    public void loadClass(String name) throws ClassNotFoundException {
        Class<?> cls = Class.forName(name);
        loadedForm = parser.parse(cls);
        
        // testovaci vypis
        Writer writer = new Writer("pokus.odml", loadedForm);
        writer.write();
    }


    public void loadPackage(String name) {
        // TODO Auto-generated method stub

    }


    public void writeLayout(OutputStream out) {
        // TODO Auto-generated method stub

    }

}
