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
 * FormDataTest.java, 3. 4. 2014 20:44:41 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;

import static org.junit.Assert.*;
import org.junit.Test;
import com.gargoylesoftware.base.testing.EqualsTester;


/**
 * Test cases for {@link FormData}.
 *
 * @author Jakub Krauz
 */
public class FormDataTest {
    
    
    /**
     * Test the equals() method.
     */
    @Test
    public void testEquals() {
        FormData a = new FormData("type", "name");
        FormData b = new FormData("type", "name");
        FormData c = new FormData("type", "anotherName");
        FormData d = new FormData("type", "name") { /* trivial subclass */ };
        
        new EqualsTester(a, b, c, d);
    }
    
    
    /**
     * Test adding items to the form data object.
     */
    @Test
    public void testAddingItems() {
        FormData data = new FormData("type", "name");
        assertEquals(0, data.getItems().size());
        data.addItem(new FormDataField("name1"));
        assertEquals(1, data.getItems().size());
        data.addItem(new FormData("type2", "name2"));
        assertEquals(2, data.getItems().size());
    }

}
