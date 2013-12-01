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
 * SimpleFormGenerator.java, 15. 11. 2013 17:36:16 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.reflections.Reflections;
import cz.zcu.kiv.formgen.Form;
import cz.zcu.kiv.formgen.FormGenerator;
import cz.zcu.kiv.formgen.FormNotFoundException;


public class SimpleFormGenerator implements FormGenerator {
    
    private ClassParser parser = new ClassParser();
    
    private List<Form> forms = new LinkedList<>();
    
    
    @Override
    public void loadClass(String name) throws ClassNotFoundException, FormNotFoundException {
        Class<?> cls = Class.forName(name);
        loadClass(cls);
    }
    
    
    @Override
    public void loadClass(Class<?> cls) throws FormNotFoundException {
        Form form = parser.parse(cls);
        forms.add(form);
    }

    
    @Override
    public void loadPackage(String name) throws FormNotFoundException {
        /*List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
            .setScanners(new SubTypesScanner(false), new ResourcesScanner())
            .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
            .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(name))));
        
        //Set<Class<? extends Object>> classes = reflections.getSubTypesOf(Object.class);  */
        
        loadPackage(Package.getPackage(name));
    }


    @Override
    public void loadPackage(Package pack) throws FormNotFoundException {
        Reflections reflections = new Reflections(pack);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(cz.zcu.kiv.formgen.annotation.Form.class);
        for (Class<?> cls : classes) {
            System.out.println(cls.getName());
            loadClass(cls);
        }
    }
    
    
    @Override
    public Form getForm(String name) {
        for (Form form : forms)
            if (form.getName().equals(name))
                return form;
        return null;
    }


    @Override
    public List<Form> getForms() {
        return forms;
    }


}
