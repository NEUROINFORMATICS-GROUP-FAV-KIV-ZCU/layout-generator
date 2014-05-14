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
 * SimpleLayoutGeneratorTest.java, 1. 2. 2014 12:06:38 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import cz.zcu.kiv.formgen.FormNotFoundException;
import cz.zcu.kiv.formgen.LayoutGenerator;
import cz.zcu.kiv.formgen.model.Form;
import example.pojo.Address;
import example.pojo.Person;


/**
 * Test cases for {@link SimpleLayoutGenerator}.
 *
 * @author Jakub Krauz
 */
public class SimpleLayoutGeneratorTest {
    
    /** The generator object being tested. */
    private LayoutGenerator generator = new SimpleLayoutGenerator();
    
    
    /**
     * Clear the loaded model before each test case.
     */
    @Before
    public void clearLoadedModel() {
        generator.clearModel();
    }
    
    
    /**
     * Test loading class.
     * @throws ClassNotFoundException
     * @throws FormNotFoundException
     */
    @Test
    public void testLoadClass_success() throws FormNotFoundException {
        Form form = generator.load(Person.class);
        
        assertNotNull(form);
        assertNull(generator.getLoadedForm("xx"));
        assertEquals(1, generator.getLoadedModel().size());
        assertNotNull(generator.getLoadedForm("Person"));
    }
    
    
    /**
     * Test loading class - null pointer.
     * @throws ClassNotFoundException
     * @throws FormNotFoundException
     */
    @Test(expected = NullPointerException.class)
    public void testLoadClass_null() throws FormNotFoundException {
        generator.load((Class<?>) null);
    }
    
    
    /**
     * Test loading class by their name.
     * @throws ClassNotFoundException
     * @throws FormNotFoundException
     */
    @Test
    public void testLoadClassByName_success() throws ClassNotFoundException, FormNotFoundException {
        Form form = generator.loadClass("example.pojo.Person");
        
        assertNotNull(form);
        assertNull(generator.getLoadedForm("xx"));
        assertEquals(1, generator.getLoadedModel().size());
        assertNotNull(generator.getLoadedForm("Person"));
    }
    
    
    /**
     * Test loading class by their name - bad name.
     * @throws ClassNotFoundException
     * @throws FormNotFoundException
     */
    @Test(expected = ClassNotFoundException.class)
    public void testLoadClassByName_badName() throws ClassNotFoundException, FormNotFoundException {
        generator.loadClass("example.Person");
    }
    
    
    /**
     * Test loading class by their name - null value.
     * @throws ClassNotFoundException
     * @throws FormNotFoundException
     */
    @Test(expected = NullPointerException.class)
    public void testLoadClassByName_null() throws ClassNotFoundException, FormNotFoundException {
        generator.loadClass((String) null);
    }
    
    
    /**
     * Test loading collection of classes.
     * @throws FormNotFoundException
     */
    @Test
    public void testLoadClasses_success() throws FormNotFoundException {
        Collection<Class<?>> classes = new ArrayList<Class<?>>(2);
        classes.add(Person.class);
        classes.add(Address.class);
        Collection<Form> model = generator.load(classes);
        assertNotNull(model);
        assertEquals(2, model.size());
        assertNotNull(generator.getLoadedForm("Address"));
        assertNotNull(generator.getLoadedForm("Person"));
    }
    
    
    /**
     * Test loading collection of classes - null.
     * @throws FormNotFoundException
     */
    @Test(expected = NullPointerException.class)
    public void testLoadClasses_null() throws FormNotFoundException {
        generator.load((Collection<Class<?>>) null);
    }
    
    
    /**
     * Test loading collection of classes by their name.
     * @throws FormNotFoundException
     * @throws ClassNotFoundException
     */
    @Test
    public void testLoadClassesByName_success() throws FormNotFoundException, ClassNotFoundException {;
        Collection<Form> model = generator.loadClasses(new String[] {"example.pojo.Person", "example.pojo.Address"});
        assertNotNull(model);
        assertEquals(2, model.size());
        assertNotNull(generator.getLoadedForm("Address"));
        assertNotNull(generator.getLoadedForm("Person"));
    }
    
    
    /**
     * Test loading collection of classes by their name - collection contains bad name.
     * @throws FormNotFoundException
     * @throws ClassNotFoundException
     */
    @Test(expected = ClassNotFoundException.class)
    public void testLoadClassesByName_badName() throws FormNotFoundException, ClassNotFoundException {;
        generator.loadClasses(new String[] {"example.pojo.Person", "example.Address"});
    }
    
    
    /**
     * Test loading collection of classes by their name - null.
     * @throws FormNotFoundException
     * @throws ClassNotFoundException
     */
    @Test(expected = NullPointerException.class)
    public void testLoadClassesByName_null() throws FormNotFoundException, ClassNotFoundException {;
        generator.loadClasses((String[]) null);
    }
    
    
    /**
     * Test loading package.
     * @throws FormNotFoundException
     */
    @Test
    public void testLoadPackage() throws FormNotFoundException {
        generator.loadPackage("example.pojo");
        
        assertEquals(2, generator.getLoadedModel().size());
        assertNotNull(generator.getLoadedForm("Person"));
    }

}
