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
 * OdmlFormProviderTest.java, 8. 1. 2014 15:30:30 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml;

import static org.junit.Assert.*;
import org.junit.Test;
import cz.zcu.kiv.formgen.core.TypeMapper;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.odml.model.OdmlForm;
import cz.zcu.kiv.formgen.odml.model.OdmlFormField;


/**
 * Contains JUnit tests for the {@link OdmlModelProvider} class.
 *
 * @author Jakub Krauz
 */
public class OdmlFormProviderTest {
    
    private OdmlModelProvider provider = new OdmlModelProvider();

    
    @Test
    public void newFormTest() {
        assertNull(provider.newForm(null));
        
        Form form = provider.newForm("test");
        assertNotNull(form);
        assertEquals(OdmlForm.class, form.getClass());
        assertEquals("test", form.getFormName());
    }
    
    
    @Test
    public void newFormItemTest() {
        assertNull(provider.newFormField(null, null));
        assertNull(provider.newFormField(null, String.class));
        assertNull(provider.newFormField("name", null));
        
        FormField formItem = provider.newFormField("name", String.class);
        assertNotNull(formItem);
        assertEquals(OdmlFormField.class, formItem.getClass());
        assertEquals("name", ((OdmlFormField) formItem).getName());
    }
    
    
    @Test
    public void typeMapperTest() {
        TypeMapper mapper = provider.typeMapper();
        assertNotNull(mapper);
        assertEquals(OdmlTypeMapper.class, mapper.getClass());
    }
    
    
}
