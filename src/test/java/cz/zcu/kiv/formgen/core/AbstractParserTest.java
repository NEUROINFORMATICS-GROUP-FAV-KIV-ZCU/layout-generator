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
 * AbstractParserTest.java, 5. 3. 2014 16:51:56 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import cz.zcu.kiv.formgen.annotation.FormItem;
import cz.zcu.kiv.formgen.model.FieldDatatype;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.model.Type;
import cz.zcu.kiv.formgen.model.constraints.Cardinality;


/**
 * Parent class for parsers test cases.
 * 
 * @author Jakub Krauz
 */
public class AbstractParserTest {
    
    
    /**
     * Test class to be parsed.
     */
    @cz.zcu.kiv.formgen.annotation.Form(label = "Label of TestClass")
    protected class TestClass {
        @FormItem(label = "ID") int id = 2;
        String str;
        @FormItem(label = "item label") Foo foo = new Foo();
    }
    
    
    /**
     * Another test class to be parsed.
     */
    protected static class Foo {
        @FormItem Byte fooItem = 3;
    }
    
    
    /**
     * Creates {@link Form} model of the TestClass.
     * @param id ID of the form.
     * @param root Whether the form is a root form.
     * @return created test form
     */
    protected Form createTestLayout(int id, boolean root) {
        Form form = new Form("TestClass");
        form.setId(id++);
        form.setLabel("Label of TestClass");
        form.setDataReference("TestClass");
        form.setCardinality(Cardinality.SINGLE_VALUE);
        if (root)
            form.setLayoutName(form.getName() + "-generated");
        FormField field = new FormField("id", Type.TEXTBOX, FieldDatatype.INTEGER);
        field.setLabel("ID");
        field.setId(id++);
        field.setCardinality(Cardinality.SINGLE_VALUE);
        form.addItem(field);
        
        Form subform = new Form("foo");
        subform.setId(id++);
        subform.setLabel("item label");
        subform.setDataReference("Foo");
        subform.setCardinality(Cardinality.SINGLE_VALUE);
        field = new FormField("fooItem", Type.TEXTBOX, FieldDatatype.INTEGER);
        field.setLabel("Foo item");
        field.setId(id++);
        field.setCardinality(Cardinality.SINGLE_VALUE);
        subform.addItem(field);
        form.addItem(subform);
        
        return form;
    }


}
