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
 * SimpleObjectBuilderTest.java, 3. 5. 2014 8:33:32 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import static org.junit.Assert.*;
import org.junit.Test;
import cz.zcu.kiv.formgen.DataGenerator;
import cz.zcu.kiv.formgen.ObjectBuilder;
import cz.zcu.kiv.formgen.ObjectBuilderException;
import cz.zcu.kiv.formgen.model.FormData;
import example.pojo.Address;
import example.pojo.Person;


/**
 * Test cases for {@link SimpleObjectBuilder}.
 *
 * @author Jakub Krauz
 */
public class SimpleObjectBuilderTest {
    
    /** The builder object to be tested. */
    private ObjectBuilder builder = new SimpleObjectBuilder();
    
    /** Data generator - the object is expected to work properly. */
    private DataGenerator generator = new SimpleDataGenerator();
    
    
    /**
     * Test a successful build process.
     * @throws ObjectBuilderException
     */
    @Test
    public void testBuild_success() throws ObjectBuilderException {
        Object obj = createTestObject();      // original object
        FormData data = generator.load(obj);  // data model
        
        // build the original object
        Object newObj = builder.build(data, obj.getClass());
        assertEquals(obj, newObj);
        
        // build the original object typed
        Person newObj2 = builder.buildTyped(data, Person.class);
        assertEquals(obj, newObj2);
    }
    
    
    /**
     * Test building incompatible type.
     * @throws ObjectBuilderException
     */
    @Test(expected = ObjectBuilderException.class)
    public void testBuild_badType() throws ObjectBuilderException {
        // data represent Person, but we require to build Address
        builder.build(generator.load(createTestObject()), Address.class);
    }
    
    
    /**
     * Test building object from null data.
     * @throws ObjectBuilderException
     */
    @Test(expected = ObjectBuilderException.class)
    public void testBuild_nullData() throws ObjectBuilderException {
        builder.build(null, Object.class);
    }
    
    
    /**
     * Test building - null required type.
     * @throws ObjectBuilderException
     */
    @Test(expected = ObjectBuilderException.class)
    public void testBuild_nullType() throws ObjectBuilderException {
        builder.build(new FormData("type", "name"), null);
    }
    
    
    /**
     * Test building typed object - incompatible type.
     * @throws ObjectBuilderException
     */
    @Test(expected = ObjectBuilderException.class)
    public void testBuildTyped_badType() throws ObjectBuilderException {
     // data represent Person, but we require to build Address
        builder.buildTyped(generator.load(createTestObject()), Address.class);
    }
    
    
    /**
     * Test building typed object from null data.
     * @throws ObjectBuilderException
     */
    @Test(expected = ObjectBuilderException.class)
    public void testBuildTyped_nullData() throws ObjectBuilderException {
        builder.buildTyped(null, Object.class);
    }
    
    
    /**
     * Test building typed object - null required type.
     * @throws ObjectBuilderException
     */
    @Test(expected = ObjectBuilderException.class)
    public void testBuildTyped_nullType() throws ObjectBuilderException {
        builder.buildTyped(new FormData("type", "name"), null);
    }
    
    
    
    /**
     * Creates an instance of Person for testing purposes.
     * @return test object
     */
    private Object createTestObject() {
        Person person = new Person(0, "Ian", 21, null);
        person.setAddress(new Address(0, "Pilsen", "Brewery st.", 321));
        return (Object) person;
    }

}
