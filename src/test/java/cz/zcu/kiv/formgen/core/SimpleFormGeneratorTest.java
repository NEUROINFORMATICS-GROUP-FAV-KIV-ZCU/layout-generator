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
 * SimpleFormGeneratorTest.java, 1. 2. 2014 12:06:38 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import static org.junit.Assert.*;
import org.junit.Test;
import cz.zcu.kiv.formgen.FormNotFoundException;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.odml.OdmlModelProvider;


/**
 *
 * @author Jakub Krauz
 */
public class SimpleFormGeneratorTest {
    
    SimpleLayoutGenerator generator;
    
    
    @Test
    public void loadClassTest() throws ClassNotFoundException, FormNotFoundException {
        generator = new SimpleLayoutGenerator(new OdmlModelProvider());
        generator.loadClass("example.pojo.Person");
        
        assertNull(generator.getForm("xx"));
        assertEquals(1, generator.getForms().size());
        
        Form form = generator.getForm("Person");
        assertNotNull(form);
    }
    
    
    @Test
    public void loadPackageTest() throws FormNotFoundException {
        generator = new SimpleLayoutGenerator(new OdmlModelProvider());
        generator.loadPackage("example.pojo");
        
        assertEquals(2, generator.getForms().size());
        assertNotNull(generator.getForm("Person"));
    }

}
