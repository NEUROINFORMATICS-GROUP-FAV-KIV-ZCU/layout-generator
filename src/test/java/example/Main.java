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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import cz.zcu.kiv.formgen.LayoutGenerator;
import cz.zcu.kiv.formgen.Reader;
import cz.zcu.kiv.formgen.Writer;
import cz.zcu.kiv.formgen.core.ObjectBuilder;
import cz.zcu.kiv.formgen.core.SimpleLayoutGenerator;
import cz.zcu.kiv.formgen.core.SimpleDataGenerator;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormData;
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
            for (Form form : gen.getLoadedModel()) {
                Writer writer = new OdmlWriter();
                OutputStream stream = new FileOutputStream("odml/" + form.getName() + ".odml");
                writer.writeLayout(form, stream);
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
        Address address = new Address(1, "Pilsen", "Zluticka", 26);
        Pokus pokus = new Pokus();
        pokus.setShortNumber((short) 20);
        pokus.setBajt((byte) 44);
        address.setPokus(pokus);
        person.setAddress(address);
        person.addAddress(address);
        person.addAddress(new Address(2, "Prague", "Brnenska", 415));
        person.setBirth(new Date());
        
        SimpleDataGenerator generator = new SimpleDataGenerator();
        generator.load(person);
        Collection<FormData> formData = generator.getLoadedModel();
        
        Writer writer = new OdmlWriter();
        OutputStream stream = new FileOutputStream("odml/pokus.odml");
        writer.writeData(formData, stream);
        stream.close();
        
        
        /* load back */
        InputStream in = new FileInputStream("odml/pokus.odml");
        Reader reader = new OdmlReader();
        Set<FormData> data = reader.readData(in);
        
        System.out.println("******* Person *********");
        System.out.println(person.toString());
        System.out.println("************************");
        
        /* build data object */
        ObjectBuilder<Person> builder = new ObjectBuilder<Person>(Person.class);
        Person tmp = builder.build(data.iterator().next());
        
        System.out.println("******* Person built *********");
        System.out.println(tmp.toString());
        System.out.println("************************");
        
        
        
        /* collection of data */
        
        /*List<Object> list = new ArrayList<Object>(3);
        list.add(new Address("Pilsen", "Zluticka", 26));
        list.add(new Address("Pilsen", "Manesova", 78));
        list.add(new Address("Pilsen", "Klatovska", 333));
        generator = new SimpleFormDataGenerator();
        generator.loadObjects(list);
        Collection<Form> forms = generator.getForms();
        System.out.println("pocet: " + forms.size());
        stream = new FileOutputStream("odml/data.odml");
        writer.write(forms, stream);
        stream.close();*/
        
        List<Object> list = new ArrayList<Object>(3);
        Person osoba = new Person(88, "Pepa", 18, new Date());
        osoba.addAddress(new Address(1, "Pilsen", "Zluticka", 26));
        osoba.addAddress(new Address(2, "Pilsen", "Manesova", 78));
        osoba.addAddress(new Address(3, "Pilsen", "Klatovska", 333));
        list.add(osoba);
        
        SimpleDataGenerator gen = new SimpleDataGenerator();
        gen.load(list);
        Collection<FormData> forms = gen.getLoadedModel();
        stream = new FileOutputStream("odml/data.odml");
        writer.writeData(forms, stream);
        stream.close();
    }
    

}
