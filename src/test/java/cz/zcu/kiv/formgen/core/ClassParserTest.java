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
import java.lang.annotation.Annotation;
import org.junit.Test;
import cz.zcu.kiv.formgen.annotation.MultiForm;
import cz.zcu.kiv.formgen.model.Form;


/**
 *
 * @author Jakub Krauz
 */
public class ClassParserTest extends AbstractParserTest {
    
    /** The class parser object. */
    private ClassParser parser = new ClassParser();
    
    
    @Test
    public void testParse_success() {
        Form form = parser.parse(TestClass.class);
        assertEquals(createTestLayout(0, true), form);
    }
    
    
    @Test(expected = NullPointerException.class)
    public void testParse_null() {
        parser.parse(null);
    }
    
    
    @Test
    public void testParse_addToForm() {
        Form form = new Form("superForm");
        parser.parse(TestClass.class, form);
        assertEquals(createTestLayout(1, false), form.getItemAt(0));
    }
    
    
    @Test(expected = NullPointerException.class)
    public void testParse_addToFormNull() throws Exception {
        Form form = new Form("superForm");
        try {
            parser.parse(null, form);
        } catch (Exception e) {
            assertEquals(new Form("superForm"), form);
            throw e;
        }
    }
    
    
    @Test
    public void testCreateMultiform_succes() {
        
        MultiForm annotation = new MultiForm() {
            
            @Override
            public Class<? extends Annotation> annotationType() {
                return MultiForm.class;
            }
            
            @Override
            public String value() { return "multi"; }
            
            @Override
            public String label() { return "label"; }
        };
        
        Form expected = new Form("multi");
        expected.setId(0);
        expected.setLayoutName("multi-generated");
        expected.setLabel("label");
        
        assertEquals(expected, parser.createMultiform(annotation));
    }
    
    
    @Test(expected = NullPointerException.class)
    public void testCreateMultiform_null() {
        parser.createMultiform(null);
    }
    

}
