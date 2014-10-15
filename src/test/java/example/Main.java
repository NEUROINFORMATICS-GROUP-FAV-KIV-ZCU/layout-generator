/***********************************************************************************************************************
 *
 * This file is part of the layout-generator project
 *
 * =================================================
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Set;

import cz.zcu.kiv.formgen.LayoutGenerator;
import cz.zcu.kiv.formgen.ObjectBuilder;
import cz.zcu.kiv.formgen.Reader;
import cz.zcu.kiv.formgen.Writer;
import cz.zcu.kiv.formgen.core.SimpleObjectBuilder;
import cz.zcu.kiv.formgen.core.SimpleLayoutGenerator;
import cz.zcu.kiv.formgen.core.SimpleDataGenerator;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormData;
import cz.zcu.kiv.formgen.odml.OdmlReader;
import cz.zcu.kiv.formgen.odml.OdmlWriter;
import cz.zcu.kiv.formgen.odml.TemplateStyle;
import example.pojo.Address;
import example.pojo.Person;


/**
 * A sample program demonstrating basic usage of the layout-generator libarry.
 *
 * @author Jakub Krauz
 */
public class Main {
    
    /** The base package of data model objects. */
    public static final String POJO_BASE = "example.pojo";
    
    /** Directory for sample outputs. */
    public static final String OUT_DIR = "example";
    
    /** Name of the file with serialized data. */
    public static final String DATA_FILE = "data.odml";

    
    /**
     * Runs a sample generation of form templates and a sample data transfer.
     * @param args not used
     */
    public static void main(String[] args) {
        // create the output directory
        createOutDir();
        
        // example of form templates generation
        generateFormLayouts();
        
        // example of data transfer
        transferData();

        System.out.println("Finished.");
    }
    
    
    
    /**
     * Demonstrates form templates generation and their serialization to odML transfer format.
     */
    private static void generateFormLayouts() {
        LayoutGenerator generator = new SimpleLayoutGenerator();  // layout generator
        Writer writer = new OdmlWriter(TemplateStyle.GUI_NAMESPACE);                         // odML writer
        
        try {
            // load and parse all classes in the base package
            generator.loadPackage(POJO_BASE);
            
            // write all generated templates to odml files
            for (Form form : generator.getLoadedModel()) {
                OutputStream stream = new FileOutputStream(OUT_DIR + "/" + form.getName() + ".odml");
                writer.writeLayout(form, stream);
                stream.close();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("Generated form templates were written to the \"" + OUT_DIR + "\" directory.\n");
    }
    
    
    /**
     * Demonstrates data transfer using odML.
     */
    private static void transferData() {
        System.out.println("Running sample data transfer...\n");
        TemplateStyle style = TemplateStyle.GUI_NAMESPACE;
        
        // prepare sample data object
        Person person = new Person(1, "Thomas", 31, null);
        person.setAddress(new Address(1, "Pilsen", "Brewery St.", 26));
        person.setBirth(new Date());
        System.out.println("*************** original data ***************");
        System.out.println(person.toString());
        System.out.println("*********************************************\n");
        
        
        // parse the data object and write it to odML file
        SimpleDataGenerator generator = new SimpleDataGenerator();
        FormData data = generator.load(person, true);
        Writer writer = new OdmlWriter(style);
        try {
            OutputStream stream = new FileOutputStream(OUT_DIR + "/" + DATA_FILE);
            writer.writeData(data, stream);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
        
        // load the data back from the odML file and build the original data object
        Reader reader = new OdmlReader(style);
        ObjectBuilder builder = new SimpleObjectBuilder();
        try {
            // read the odML
            InputStream in = new FileInputStream(OUT_DIR + "/" + DATA_FILE);
            Set<FormData> loadedData = reader.readData(in);
            
            // build the original object
            Person person2 = builder.buildTyped(loadedData.iterator().next(), Person.class);
            System.out.println("************ data after transfer ************");
            System.out.println(person2.toString());
            System.out.println("*********************************************\n");
            
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        
    }
    
    
    /**
     * Create the output directory.
     */
    private static void createOutDir() {
        File outDir = new File(OUT_DIR);
        outDir.mkdir();
    }

}
