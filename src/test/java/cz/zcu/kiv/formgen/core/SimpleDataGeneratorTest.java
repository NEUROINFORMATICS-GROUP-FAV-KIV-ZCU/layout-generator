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
 * SimpleDataGeneratorTest.java, 3. 5. 2014 8:32:45 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import cz.zcu.kiv.formgen.DataGenerator;
import cz.zcu.kiv.formgen.model.FormData;
import example.pojo.Address;
import example.pojo.Person;


/**
 * Test cases for {@link SimpleDataGenerator}.
 *
 * @author Jakub Krauz
 */
public class SimpleDataGeneratorTest {

    /** The generator object to be tested. */
    private DataGenerator generator = new SimpleDataGenerator();
    
    
    /**
     * Clear the loaded model before each test case.
     */
    @Before
    public void clearLoadedModel() {
        generator.clearModel();
    }
    
    
    /**
     * Test loading single object.
     */
    @Test
    public void testLoad_single() {
        // prepare test object
        Person person = new Person(2, "Ian", 35, new Date());

        FormData data = generator.load(person);
        assertNotNull(data);
        assertEquals(1, generator.getLoadedModel().size());
        assertSame(generator.getLoadedModel().iterator().next(), data);
    }
    
    
    /**
     * Test loading collection.
     */
    @Test
    public void testLoad_collection() {
        // prepare test collection
        Collection<Object> persons = new ArrayList<Object>(2);
        persons.add(new Person(2, "Ian", 35, new Date()));
        persons.add(new Person(3, "Thomas", 18, null));

        Collection<FormData> data2 = generator.load(persons);
        assertNotNull(data2);
        assertEquals(2, data2.size());
        assertEquals(2, generator.getLoadedModel().size());
        assertEquals(generator.getLoadedModel(), data2);
    }
    
    
    /**
     * Test using the <code>includeReference</code> argument.
     */
    @Test
    public void testLoad_reference() {
        // prepare test object
        Person person = new Person(2, "Ian", 35, new Date());
        person.setAddress(new Address(1, "Pilsen", "Brewery St.", 33));
        Collection<Object> persons = new ArrayList<Object>(1);
        persons.add(person);
        
        FormData data = generator.load(person);
        assertEquals(data, generator.load(person, true));
        assertNotEquals(data, generator.load(person, false));
        
        Collection<FormData> data2 = generator.load(persons);
        assertEquals(data2, generator.load(persons, true));
        assertNotEquals(data2, generator.load(persons, false));
    }
    
    
    /**
     * Test loading null value - NullPointerException expected.
     */
    @Test(expected = NullPointerException.class)
    public void testLoad_null() {
        generator.load(null);
    }
    

}
