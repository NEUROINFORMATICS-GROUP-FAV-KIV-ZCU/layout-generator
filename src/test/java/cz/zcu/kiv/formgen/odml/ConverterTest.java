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
 * ConverterTest.java, 26. 3. 2014 10:48:29 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml;

import static org.junit.Assert.*;
import java.util.Set;
import odml.core.Section;
import org.junit.Test;
import cz.zcu.kiv.formgen.model.FieldDatatype;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormData;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.model.Type;


/**
 *
 * @author Jakub Krauz
 */
public class ConverterTest {
    
    private Converter converter = new Converter();
    
    
    public ConverterTest() {
       /*try {
            OdmlWriter odmlWriter = new OdmlWriter();
            OutputStream os = new FileOutputStream("form.odml");
            odmlWriter.write(form, os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            Section root = new Section();
            root.add(section);
            Writer writer = new Writer(root);
            OutputStream os = new FileOutputStream("section.odml");
            writer.write(os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        } */
    }
    
    
    @Test
    public void testModelToOdml() throws OdmlConvertException {
        Section section = converter.layoutToOdml(createTestForm());
        assertEquals(createTestSection(), section);
    }
    
    
    @Test(expected = NullPointerException.class)
    public void testModelToOdml_null() throws OdmlConvertException {
        converter.layoutToOdml((Form) null);
    }
    
    
    @Test
    public void testOdmlDataToModel() throws OdmlConvertException {
        Set<FormData> data = converter.odmlToDataModel(createTestDataSection());
        assertEquals(createTestDataForm(), data.toArray()[0]);
    }
    
    
    @Test(expected = NullPointerException.class)
    public void testOdmlDataToModel_null() throws OdmlConvertException {
        converter.odmlToDataModel(null);
    }
    
    
    
    private Form createTestForm() {
        Form form = new Form("form");
        //form.setLayout(true);
        form.addItem(new FormField("field", Type.CHECKBOX, FieldDatatype.STRING));
        Form subform = new Form("subform");
        subform.addItem(new FormField("field2", Type.TEXTBOX, FieldDatatype.EMAIL));
        form.addItem(subform);
        return form;
    }
    
    
    private Section createTestSection() {
        Section section = null;
        
        try {
            section = new Section("form", Type.FORM.toString());
            
            Section field = new Section("field", Type.CHECKBOX.toString());
            field.addProperty("required", false);
            field.addProperty("datatype", FieldDatatype.STRING.toString());
            section.add(field);
            
            Section subform = new Section("subform", Type.FORM.toString());
            subform.addProperty("required", false);
            subform.addProperty("idTop", 0);
            Section field2 = new Section("field2", Type.TEXTBOX.toString());
            field2.addProperty("required", false);
            field2.addProperty("datatype", FieldDatatype.EMAIL.toString());
            subform.add(field2);
            section.add(subform);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return section;
    }
    
    
    private Form createTestDataForm() {
        Form form = new Form("form");
        //form.setLayout(false);
        Form subform = new Form("subform");
        FormField field = new FormField("textbox");
        //field.setValue("text");
        subform.addItem(field);
        form.addItem(subform);
        field = new FormField("checkbox");
        //field.setValue(Boolean.TRUE);
        form.addItem(field);
        return form;
    }
    
    
    private Section createTestDataSection() {
        Section section = null;
        
        try {
            section = new Section("form", Type.FORM.toString());
            section.addProperty("checkbox", Boolean.TRUE);
            Section subform = new Section("subform", Type.FORM.toString());
            subform.addProperty("textbox", "text");
            section.add(subform);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return section;
    }


}
