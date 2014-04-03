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
 * FormDataFieldTest.java, 3. 4. 2014 20:44:54 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;

import org.junit.Test;
import com.gargoylesoftware.base.testing.EqualsTester;


/**
 *
 * @author Jakub Krauz
 */
public class FormDataFieldTest {
    
    
    @Test
    public void testEquals() {
        FormDataField a = new FormDataField("type", "name");
        FormDataField b = new FormDataField("type", "name");
        FormDataField c = new FormDataField("type", "anotherName");
        FormDataField d = new FormDataField("type", "name") { /* trivial subclass */ };
        
        new EqualsTester(a, b, c, d);
    }

}
