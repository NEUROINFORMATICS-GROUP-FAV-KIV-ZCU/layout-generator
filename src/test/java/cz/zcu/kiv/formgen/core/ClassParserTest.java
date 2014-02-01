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
 * ClassParserTest.java, 1. 2. 2014 12:05:27 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import static org.junit.Assert.*;
import org.junit.Test;
import cz.zcu.kiv.formgen.Form;
import cz.zcu.kiv.formgen.FormNotFoundException;
import cz.zcu.kiv.formgen.odml.OdmlFormProvider;


/**
 *
 * @author Jakub Krauz
 */
public class ClassParserTest {
    
    private ClassParser parser = new ClassParser(new OdmlFormProvider());
    
    
    // TODO test cases
    
    @Test
    public void parseTest() throws FormNotFoundException {
        Class<?> cls;
        try {
            cls = Class.forName("example.pojo.Person");
            Form form = parser.parse(cls);
            assertNotNull(form);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
