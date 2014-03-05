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
import cz.zcu.kiv.formgen.annotation.FormItem;
import cz.zcu.kiv.formgen.annotation.MultiForm;
import cz.zcu.kiv.formgen.model.FieldDatatype;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.model.Type;


/**
 *
 * @author Jakub Krauz
 */
public class ClassParserTest {
    
    /** The class parser object. */
    private ClassParser parser = new ClassParser();
    
    
    @Test
    public void testParse_success() {
        Form form = parser.parse(TestClass.class);
        assertEquals(createTestForm(0, true), form);
    }
    
    
    @Test(expected = NullPointerException.class)
    public void testParse_null() {
        parser.parse(null);
    }
    
    
    @Test
    public void testParse_addToForm() {
        Form form = new Form("superForm");
        parser.parse(TestClass.class, form);
        assertEquals(createTestForm(1, false), form.getItemAt(0));
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
    public void testCreateMultiform() {
        
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
    
    
    
    
    private Form createTestForm(int id, boolean root) {
        Form form = new Form("TestClass");
        form.setId(id++);
        form.setLabel("Label of TestClass");
        if (root) {
            form.setLayout(true);
            form.setLayoutName(form.getName() + "-generated");
        }
        cz.zcu.kiv.formgen.model.FormItem item = new FormField("id", Type.TEXTBOX, FieldDatatype.INTEGER);
        item.setLabel("ID");
        item.setId(id++);
        form.addItem(item);
        
        Form subform = new Form("foo");
        subform.setId(id++);
        subform.setLabel("item label");
        item = new FormField("fooItem", Type.TEXTBOX, FieldDatatype.INTEGER);
        item.setLabel("fooItem");
        item.setId(id++);
        subform.addItem(item);
        form.addItem(subform);
        
        return form;
    }
    
    
    
    @cz.zcu.kiv.formgen.annotation.Form(label = "Label of TestClass")
    private class TestClass {
        @FormItem(label = "ID") int id;
        @SuppressWarnings("unused") String str;
        @FormItem(label = "item label") Foo foo;
    }
    
    
    private class Foo {
        @FormItem Byte fooItem;
    }

}
