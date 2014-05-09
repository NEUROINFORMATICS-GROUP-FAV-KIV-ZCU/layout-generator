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
 * FormTest.java, 3. 3. 2014 21:32:33 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;

import static org.junit.Assert.*;
import org.junit.Test;
import com.gargoylesoftware.base.testing.EqualsTester;


/**
 * Test cases for {@link Form}.
 *
 * @author Jakub Krauz
 */
public class FormTest {
    
    
    /**
     * Test the equals() method.
     */
    @Test
    public void testEquals() {
        Form a = new Form("name");
        Form b = new Form("name");
        Form c = new Form("anotherName");
        Form d = new Form("name") { /* trivial subclass */ };
        
        new EqualsTester(a, b, c, d);
    }
    
    
    /**
     * Test adding items to the form.
     */
    @Test
    public void testAddingItems() {
        Form form = new Form("form");
        
        // empty
        assertEquals(0, form.countItems());
        assertEquals(0, form.highestItemId());
        assertNull(form.getItemAt(0));
        
        // null item - nothing changes
        form.addItem(null);
        assertEquals(0, form.countItems());
        assertEquals(0, form.highestItemId());
        assertNull(form.getItemAt(0));
        
        final int subformId = 3;
        FormItem item = new Form("subform");
        item.setId(subformId);
        form.addItem(item);
        assertEquals(1, form.countItems());
        assertEquals(subformId, form.highestItemId());
        assertEquals(item, form.getItemAt(0));
        
        // add field
        final int itemId = 5;
        item = new FormField("field");
        item.setId(itemId);
        form.addItem(item);
        assertEquals(2, form.countItems());
        assertEquals(itemId, form.highestItemId());
        assertEquals(item, form.getItemAt(1));
        
        assertNull(form.getItemAt(2));
    }

}
