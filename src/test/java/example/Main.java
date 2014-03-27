/***********************************************************************************************************************
 *
 * This file is part of the layout-generator project
 *
 * ==========================================
 *
 * Copyright (C) 2013 by University of West Bohemia (http://www.zcu.cz/en/)
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
 * Main.java, 15. 11. 2013 17:36:16 Jakub Krauz
 *
 **********************************************************************************************************************/

package example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import cz.zcu.kiv.formgen.FormDataGenerator;
import cz.zcu.kiv.formgen.LayoutGenerator;
import cz.zcu.kiv.formgen.Reader;
import cz.zcu.kiv.formgen.Writer;
import cz.zcu.kiv.formgen.core.ObjectBuilder;
import cz.zcu.kiv.formgen.core.SimpleFormDataGenerator;
import cz.zcu.kiv.formgen.core.SimpleLayoutGenerator;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.model.FormItem;
import cz.zcu.kiv.formgen.model.FormItemContainer;
import cz.zcu.kiv.formgen.odml.OdmlReader;
import cz.zcu.kiv.formgen.odml.OdmlWriter;
import example.pojo.Address;
import example.pojo.Person;
import example.pojo.Pokus;


public class Main {

    public static void main(String[] args) throws Exception {
        
        data();
        
        LayoutGenerator gen = new SimpleLayoutGenerator();
        //Package pack = Package.getPackage("example.pojo");
        try {
            gen.loadPackage("example.pojo");
            //gen.loadClass("example.pojo.Pokus");
            for (Form form : gen.getForms()) {
                Writer writer = new OdmlWriter();
                OutputStream stream = new FileOutputStream("odml/" + form.getName() + ".odml");
                writer.write(form, stream);
                stream.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("hotovo");

    }
    
    
    
    private static void data() throws Exception {
        Person person = new Person(0, "Thomas", 31, null);
        Address address = new Address("Pilsen", "Zluticka", 26);
        Pokus pokus = new Pokus();
        pokus.setShortNumber((short) 20);
        pokus.setBajt((byte) 44);
        address.setPokus(pokus);
        person.setAddress(address);
        person.addAddress(address);
        person.addAddress(new Address("Prague", "Brnenska", 415));
        person.setBirth(new Date());
        
        FormDataGenerator generator = new SimpleFormDataGenerator();
        generator.load(person);
        Form form = generator.getForm("Person");
        
        Writer writer = new OdmlWriter();
        OutputStream stream = new FileOutputStream("pokus.odml");
        writer.write(form, stream);
        stream.close();
        
        
        /* load back */
        InputStream in = new FileInputStream("pokus.odml");
        Reader reader = new OdmlReader();
        form = reader.read(in);
        
        System.out.println("******* Person *********");
        System.out.println(person.toString());
        System.out.println("************************");
        
        /* build data object */
        ObjectBuilder<Person> builder = new ObjectBuilder<Person>(Person.class);
        Person tmp = builder.build(form);
        
        System.out.println("******* Person built *********");
        System.out.println(tmp.toString());
        System.out.println("************************");
    }
    

}
