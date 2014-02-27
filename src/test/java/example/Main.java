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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import cz.zcu.kiv.formgen.LayoutGenerator;
import cz.zcu.kiv.formgen.Writer;
import cz.zcu.kiv.formgen.core.ObjectParser;
import cz.zcu.kiv.formgen.core.SimpleLayoutGenerator;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.odml.OdmlModelProvider;
import cz.zcu.kiv.formgen.odml.OdmlWriter;
import example.pojo.Address;
import example.pojo.Person;


public class Main {

    public static void main(String[] args) throws IOException {
        
        pokus();
        
        LayoutGenerator gen = new SimpleLayoutGenerator(new OdmlModelProvider());
        //Package pack = Package.getPackage("example.pojo");
        try {
            gen.loadPackage("example.pojo");
            //gen.loadClass("example.pojo.Pokus");
            for (Form form : gen.getForms()) {
                Writer writer = new OdmlWriter();
                OutputStream stream = new FileOutputStream(form.getFormName() + ".odml");
                writer.write(form, stream);
                stream.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("hotovo");

    }
    
    
    
    private static void pokus() throws IOException {
        Address address = new Address("Plzen", "Manesova", 78);
        Person person = new Person(0, "Jakub", 24, null);
        person.setAddress(address);
        //person.addAddress(address);
        //person.addAddress(new Address("Litice", "Vetrna", 33));
        ObjectParser parser = new ObjectParser(new OdmlModelProvider());
        Form form = parser.parse(person);
        
        Writer writer = new OdmlWriter();
        OutputStream stream = new FileOutputStream("pokus.odml");
        writer.write(form, stream);
        stream.close();
    }
    

}
