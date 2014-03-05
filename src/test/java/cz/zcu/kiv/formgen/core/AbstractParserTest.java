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
 * AbstractParserTest.java, 5. 3. 2014 16:51:56 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import cz.zcu.kiv.formgen.annotation.FormItem;
import cz.zcu.kiv.formgen.model.FieldDatatype;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.model.Type;


/**
 *
 * @author Jakub Krauz
 */
public class AbstractParserTest {
    
    
    @cz.zcu.kiv.formgen.annotation.Form(label = "Label of TestClass")
    protected class TestClass {
        @FormItem(label = "ID") int id = 2;
        String str;
        @FormItem(label = "item label") Foo foo = new Foo();
    }
    
    
    protected static class Foo {
        @FormItem Byte fooItem = 3;
    }
    
    
    protected Form createTestLayout(int id, boolean root) {
        Form form = new Form("TestClass");
        form.setId(id++);
        form.setLabel("Label of TestClass");
        if (root) {
            form.setLayout(true);
            form.setLayoutName(form.getName() + "-generated");
        }
        FormField field = new FormField("id", Type.TEXTBOX, FieldDatatype.INTEGER);
        field.setLabel("ID");
        field.setId(id++);
        form.addItem(field);
        
        Form subform = new Form("foo");
        subform.setId(id++);
        subform.setLabel("item label");
        field = new FormField("fooItem", Type.TEXTBOX, FieldDatatype.INTEGER);
        field.setLabel("fooItem");
        field.setId(id++);
        subform.addItem(field);
        form.addItem(subform);
        
        return form;
    }
    
    
    protected Form createTestFormData() {
        Form form = new Form("TestClass");
        
        FormField field = new FormField("id", Type.TEXTBOX, FieldDatatype.INTEGER);
        field.setValue((Integer) 2);
        form.addItem(field);
        
        Form subform = new Form("foo");
        field = new FormField("fooItem", Type.TEXTBOX, FieldDatatype.INTEGER);
        field.setValue(new Byte((byte) 3));
        subform.addItem(field);
        form.addItem(subform);
        
        return form;
    }


}
