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
 * OdmlFormTest.java, 23. 1. 2014 11:24:42 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml;

import static org.junit.Assert.*;
import org.junit.Test;


/**
 * Contains JUnit tests for the {@link OdmlForm} class.
 *
 * @author Jakub Krauz
 */
public class OdmlFormTest {
    
    private final String formName = "someFormName";
    
    private OdmlForm odmlForm;
    
    
    public OdmlFormTest() {
        try {
            odmlForm = new OdmlForm(formName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    @Test
    public void addItemTest() {
        assertNotNull(odmlForm);
        final String sectionName = "someSubsection";
        assertFalse(odmlForm.containsSection(sectionName));
        
        try {
            OdmlFormField item = new OdmlFormField(sectionName, String.class);
            odmlForm.addItem(item);
            assertTrue(odmlForm.containsSection(sectionName));
            assertEquals(item, odmlForm.getSection(sectionName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    @Test
    public void addSubformTest() {
        assertNotNull(odmlForm);
        final String subformName = "someSubform";
        assertFalse(odmlForm.containsSection(subformName));
        
        try {
            OdmlForm subform = new OdmlForm(subformName);
            odmlForm.addSubform(subform);
            assertTrue(odmlForm.containsSection(subformName));
            assertEquals(subform, odmlForm.getSection(subformName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    @Test
    public void setDescriptionTest() {
        assertNotNull(odmlForm);
        
        final String description = "some description";
        odmlForm.setDescription(description);
        assertEquals(description, odmlForm.getDescription());
        
        odmlForm.setDescription(null);
        assertNull(odmlForm.getDescription());
    }

}
