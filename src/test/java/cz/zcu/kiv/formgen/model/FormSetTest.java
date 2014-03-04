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
 * FormSetTest.java, 3. 3. 2014 21:32:46 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;

import org.junit.Test;
import com.gargoylesoftware.base.testing.EqualsTester;


/**
 *
 * @author Jakub Krauz
 */
public class FormSetTest {
    
    
    @Test
    public void testEquals() {
        FormSet a = new FormSet("name", Type.FORM);
        FormSet b = new FormSet("name", Type.FORM);
        FormSet c = new FormSet("name", Type.TEXTBOX);
        FormSet d = new FormSet("name", Type.FORM) { /* trivial subclass */ };
        
        new EqualsTester(a, b, c, d);
    }
    
    
    @Test(expected = BadItemTypeException.class)
    public void testAddBadItemType() {
        FormSet set = new FormSet("set", Type.TEXTBOX);
        FormField field = new FormField("field", Type.CHECKBOX, null);
        set.addItem(field);
    }

}
