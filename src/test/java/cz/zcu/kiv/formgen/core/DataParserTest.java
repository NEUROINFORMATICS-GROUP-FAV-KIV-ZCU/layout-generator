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
 * DataParserTest.java, 5. 3. 2014 16:51:41 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import static org.junit.Assert.*;
import org.junit.Test;
import cz.zcu.kiv.formgen.model.FormData;


/**
 * Test cases for {@link DataParser}.
 *
 * @author Jakub Krauz
 */
public class DataParserTest extends AbstractParserTest {
    
    /** The data parser object. */
    private DataParser parser = new DataParser();
    
    
    /**
     * Test a successful parsing process.
     */
    @Test
    public void testParse_success() {
        TestClass obj = new TestClass();
        
        // include referenced data as well
        FormData data = parser.parse(obj, true);
        assertEquals(createTestData(true), data);
        
        // include only IDs of referenced data
        data = parser.parse(obj, false);
        assertEquals(createTestData(false), data);
    }
    
    
    /**
     * Test parsing null value.
     */
    @Test(expected = NullPointerException.class)
    public void testParse_null() {
        parser.parse(null, false);
    }
    

}
