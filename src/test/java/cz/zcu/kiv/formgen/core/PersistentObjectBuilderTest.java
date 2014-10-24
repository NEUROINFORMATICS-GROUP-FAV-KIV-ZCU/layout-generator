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
 * PersistentObjectBuilderTest.java, 4. 5. 2014 15:55:04 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import cz.zcu.kiv.formgen.DataGenerator;
import cz.zcu.kiv.formgen.ObjectBuilder;
import cz.zcu.kiv.formgen.ObjectBuilderException;
import cz.zcu.kiv.formgen.PersistentObjectException;
import cz.zcu.kiv.formgen.PersistentObjectProvider;
import cz.zcu.kiv.formgen.model.FormData;
import example.pojo.Address;
import example.pojo.Person;


/**
 * Test cases for {@link PersistentObjectBuilder}.
 *
 * @author Jakub Krauz
 */
public class PersistentObjectBuilderTest {

    /** The builder object to be tested. */
    private ObjectBuilder builder = new PersistentObjectBuilder<Integer>(new TestObjectProvider());
    
    /** Data generator - the object is expected to work properly. */
    private DataGenerator generator = new SimpleDataGenerator();
    
    
    /**
     * Test a successful build using persistent objects by TestObjectProvider.
     * @throws ObjectBuilderException
     */
    @Test
    public void testBuild() throws ObjectBuilderException {
        Person original = createTestPerson();              // original object
        FormData data = generator.load(original, false);   // data model
        data.setId(null);  // remove id from the root record
                           // so as objectProvider is used only for referenced records
        
        // build the original object
        Object newObj = builder.build(data, original.getClass());
        assertEquals(original, newObj);
        
        // build the original object typed
        Person newObj2 = builder.buildTyped(data, Person.class);
        assertEquals(original, newObj2);
    }
    
    
    
    /**
     * Creates a test Person object.
     * @return test person
     */
    private Person createTestPerson() {
        Person person = new Person(0, "Thomas", 25, null, 'M');
        person.setAddress(createTestAddress());
        return person;
    }
    
    
    /**
     * Creates a test Address object.
     * @return test address
     */
    private Address createTestAddress() {
        return new Address(2, "Pilsen", "Brewery st.", 55);
    }
    
    
    
    /**
     * Provides mock persistent objects.
     */
    private class TestObjectProvider implements PersistentObjectProvider<Integer> {

        @Override
        public Object getById(Class<?> cls, Integer id) throws PersistentObjectException {
            if (cls.equals(Person.class))
                return createTestPerson();
            else if (cls.equals(Address.class))
                return createTestAddress();
            else
                return null;
        }
        
    }

    
}
