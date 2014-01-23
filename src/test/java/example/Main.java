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
import java.io.OutputStream;
import cz.zcu.kiv.formgen.Form;
import cz.zcu.kiv.formgen.FormGenerator;
import cz.zcu.kiv.formgen.Writer;
import cz.zcu.kiv.formgen.core.SimpleFormGenerator;
import cz.zcu.kiv.formgen.odml.OdmlFormProvider;
import cz.zcu.kiv.formgen.odml.OdmlWriter;


public class Main {

    public static void main(String[] args) {
        
        //loadPerson();
        
        FormGenerator gen = new SimpleFormGenerator(new OdmlFormProvider());
        //Package pack = Package.getPackage("example.pojo");
        try {
            gen.loadPackage("example.pojo");
            //gen.loadClass("example.pojo.Pokus");
            for (Form form : gen.getForms()) {
                Writer writer = new OdmlWriter();
                OutputStream stream = new FileOutputStream(form.getName() + ".odml");
                writer.write(form, stream);
                stream.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("hotovo");

    }
    
    
    private static void loadPerson() {
        FormGenerator gen = new SimpleFormGenerator(new OdmlFormProvider());
        try {
            gen.loadClass("example.pojo.Person");
            Form model = gen.getForm("Osoba");
            
            // testovaci vypis
            if (model != null) {
                OutputStream stream = new FileOutputStream("pokus.odml");
                Writer writer = new OdmlWriter();
                writer.write(model, stream);
                stream.close();
            } else {
                System.out.println("null model");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
