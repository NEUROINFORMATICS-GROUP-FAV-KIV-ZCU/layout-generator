/***********************************************************************************************************************
 *
 * This file is part of the odml-form-generator project
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

import odml.core.Writer;
import cz.zcu.kiv.formgen.FormGenerator;
import cz.zcu.kiv.formgen.FormNotFoundException;
import cz.zcu.kiv.formgen.odml.OdmlFormGenerator;
import cz.zcu.kiv.formgen.odml.OdmlFormModel;


public class Main {

    public static void main(String[] args) {
        FormGenerator gen = new OdmlFormGenerator();
        try {
            gen.loadClass("example.pojo.Person");
            OdmlFormModel model = (OdmlFormModel) gen.getModel("Osoba");
            
            // testovaci vypis
            if (model != null) {
                Writer writer = new Writer("pokus.odml", model.getRootSection());
                writer.write();
            } else {
                System.out.println("null model");
            }
        } catch (ClassNotFoundException | FormNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
