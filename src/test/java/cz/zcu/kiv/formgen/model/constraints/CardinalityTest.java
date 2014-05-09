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
 * CardinalityTest.java, 9. 5. 2014 9:41:46 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model.constraints;

import org.junit.Test;
import com.gargoylesoftware.base.testing.EqualsTester;


/**
 * Test cases for {@link Cardinality}.
 *
 * @author Jakub Krauz
 */
public class CardinalityTest {

    
    /**
     * Test the equals() method.
     */
    @Test
    public void testEquals() {
        Cardinality a = Cardinality.SINGLE_VALUE;
        Cardinality b = new Cardinality(1);
        Cardinality c = Cardinality.UNRESTRICTED;
        Cardinality d = new Cardinality(1) { /* trivial subclass */ };
        
        new EqualsTester(a, b, c, d);
    }

}
