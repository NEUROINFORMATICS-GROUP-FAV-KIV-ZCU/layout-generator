/***********************************************************************************************************************
 *
 * This file is part of the form-generator project
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
 * OdmlFormGenerator.java, 15. 11. 2013 17:36:16 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml;

import java.util.LinkedList;
import java.util.List;
import odml.core.Section;
import cz.zcu.kiv.formgen.FormGenerator;
import cz.zcu.kiv.formgen.FormModel;
import cz.zcu.kiv.formgen.FormNotFoundException;


public class OdmlFormGenerator implements FormGenerator {
    
    private ClassParser parser = new ClassParser();
    
    private List<FormModel> models = new LinkedList<>();
    
    
    @Override
    public void loadClass(String name) throws ClassNotFoundException, FormNotFoundException {
        Class<?> cls = Class.forName(name);
        Section rootSection = parser.parse(cls);
        models.add(new OdmlFormModel(rootSection.getName(), rootSection));
        
        System.out.println("loaded OdmlFormModel: " + rootSection.getName());
    }

    
    @Override
    public void loadPackage(String name) {
        // TODO Auto-generated method stub

    }


    @Override
    public FormModel getModel(String name) {
        for (FormModel model : models)
            if (model.getName().equals(name))
                return model;
        return null;
    }


    @Override
    public List<FormModel> getModels() {
        return models;
    }

}
