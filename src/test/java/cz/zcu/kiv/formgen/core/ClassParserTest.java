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
import cz.zcu.kiv.formgen.FormNotFoundException;
import cz.zcu.kiv.formgen.annotation.FormItem;
import cz.zcu.kiv.formgen.model.FieldDatatype;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.model.Type;


/**
 *
 * @author Jakub Krauz
 */
public class ClassParserTest {
    
    private ClassParser parser = new ClassParser();
    
    
    // TODO more test cases
    
    @Test
    public void testParse() throws FormNotFoundException {
        Form form = parser.parse(TestClass.class);
        assertEquals(createTestForm(), form);
    }
    
    
    
    private Form createTestForm() {
        Form form = new Form("TestClass");
        form.setId(0);
        form.setLabel("TestClass");
        form.setLayout(true);
        form.setLayoutName(form.getName() + "-generated");
        cz.zcu.kiv.formgen.model.FormItem item = new FormField("id", Type.TEXTBOX, FieldDatatype.INTEGER);
        item.setLabel("ID");
        item.setId(1);
        form.addItem(item);
        
        Form subform = new Form("foo");
        subform.setId(2);
        subform.setLabel("FOO_FORM");
        item = new FormField("item", Type.TEXTBOX, FieldDatatype.INTEGER);
        item.setLabel("item");
        item.setId(3);
        subform.addItem(item);
        form.addItem(subform);
        
        return form;
    }
    
    
    
    @cz.zcu.kiv.formgen.annotation.Form
    private class TestClass {
        @FormItem(label = "ID") int id;
        @SuppressWarnings("unused") String str;
        @FormItem Foo foo;
    }
    
    
    @cz.zcu.kiv.formgen.annotation.Form(label = "FOO_FORM")
    private class Foo {
        @FormItem Byte item;
    }

}
