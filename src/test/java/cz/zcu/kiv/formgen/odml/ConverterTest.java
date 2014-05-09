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
 * ConverterTest.java, 26. 3. 2014 10:48:29 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml;

import static org.junit.Assert.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import odml.core.Section;
import org.junit.Test;
import cz.zcu.kiv.formgen.model.FieldDatatype;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormData;
import cz.zcu.kiv.formgen.model.FormDataField;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.model.Type;


/**
 * Test cases for {@link Converter}.
 *
 * @author Jakub Krauz
 */
public class ConverterTest {
    
    /** The converter object being tested. */
    private Converter converter = new Converter();
    
    
    /**
     * Test converting {@link Form} model to odML.
     * @throws OdmlConvertException
     */
    @Test
    public void testLayoutToOdml() throws OdmlConvertException {
        // single layout
        Section section = converter.layoutToOdml(createTestForm());
        assertEquals(createTestSection(), section);
        
        // collection
        Collection<Form> forms = new HashSet<Form>(1);
        forms.add(createTestForm());
        Section rootSection = new Section();
        rootSection.add(createTestSection());
        assertEquals(rootSection, converter.layoutToOdml(forms));
    }
    
    
    /**
     * Test converting {@link FormData} model to odML.
     * @throws OdmlConvertException
     */
    @Test
    public void testDataToOdml() throws OdmlConvertException {
        // single formData
        Section section = converter.dataToOdml(createTestFormData());
        assertEquals(createTestDataSection(), section);
        
        // collection
        Collection<FormData> data = new HashSet<FormData>(1);
        data.add(createTestFormData());
        Section rootSection = new Section();
        rootSection.add(createTestDataSection());
        assertEquals(rootSection, converter.dataToOdml(data));
    }
    
    
    /**
     * Test converting null arguments.
     * @throws OdmlConvertException
     */
    @Test
    public void testNullArguments() throws OdmlConvertException {
        assertNull(converter.layoutToOdml((Form) null));
        assertNull(converter.layoutToOdml((Collection<Form>) null));
        assertNull(converter.dataToOdml((FormData) null));
        assertNull(converter.dataToOdml((Collection<FormData>) null));
        assertNull(converter.odmlToLayoutModel((Section) null));
        assertNull(converter.odmlToDataModel((Section) null));
    }
    
    
    /**
     * Test converting odML to {@link FormData} model.
     * @throws OdmlConvertException
     */
    @Test
    public void testOdmlToDataModel() throws OdmlConvertException {
        Section dataRoot = new Section();
        dataRoot.add(createTestDataSection());
        Set<FormData> data = converter.odmlToDataModel(dataRoot);
        assertEquals(1, data.size());
        assertEquals(createTestFormData(), data.toArray()[0]);
    }

    
    
    /**
     * Creates a test {@link Form} model.
     * @return test form
     */
    private Form createTestForm() {
        Form form = new Form("form");
        form.addItem(new FormField("field", Type.CHECKBOX, FieldDatatype.STRING));
        Form subform = new Form("subform");
        subform.addItem(new FormField("field2", Type.TEXTBOX, FieldDatatype.EMAIL));
        form.addItem(subform);
        return form;
    }
    
    
    /**
     * Creates a test section corresponding to the test form created by {@link #createTestForm()}.
     * @return test section
     */
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
    
    
    /**
     * Creates test {@link FormData} model.
     * @return test form data
     */
    private FormData createTestFormData() {
        FormData data = new FormData("type", "name");
        
        FormData inner = new FormData("innerType", "innerName");
        FormDataField field = new FormDataField("string", "textbox");
        field.setValue("text");
        inner.addItem(field);
        data.addItem(inner);
        
        field = new FormDataField("boolean", "checkbox");
        field.setValue(Boolean.TRUE);
        data.addItem(field);
        
        return data;
    }
    
    
    /**
     * Creates a test section corresponding to the test form data created by {@link #createTestFormData()}.
     * @return test data section
     */
    private Section createTestDataSection() {
        Section section = null;
        
        try {
            section = new Section("name", "type");
            section.addProperty("checkbox", Boolean.TRUE);
            Section subform = new Section("innerName", "innerType");
            subform.addProperty("textbox", "text");
            section.add(subform);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return section;
    }


}
