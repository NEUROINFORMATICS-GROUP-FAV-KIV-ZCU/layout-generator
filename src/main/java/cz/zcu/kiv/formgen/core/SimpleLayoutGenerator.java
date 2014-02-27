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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import cz.zcu.kiv.formgen.LayoutGenerator;
import cz.zcu.kiv.formgen.FormNotFoundException;
import cz.zcu.kiv.formgen.model.Form;


/**
 * Generates form layout from a POJO data model.
 *
 * @author Jakub Krauz
 */
public class SimpleLayoutGenerator implements LayoutGenerator {
    
    /** Class parser instance. */
    private ClassParser parser;
    
    /** Map of created forms by their name. */
    private Map<String, Form> forms = new HashMap<String, Form>();
    
    
    /**
     * Constructs new generator using the given {@link FormProvider} object.
     * @param formProvider
     */
    public SimpleLayoutGenerator(FormProvider formProvider) {
        parser = new ClassParser(formProvider);
    }
    
    
    @Override
    public void loadClass(String... names) throws ClassNotFoundException, FormNotFoundException {
        Class<?>[] classes = new Class<?>[names.length];
        for (int i = 0; i < classes.length; i++)
            classes[i] = Class.forName(names[i]);
        load(classes);
    }
    
    
    @Override
    public void load(Class<?>... cls) throws FormNotFoundException {
        for (Class<?> c : cls) {
            if (c.isAnnotationPresent(cz.zcu.kiv.formgen.annotation.Form.class))
                loadClass(c, false);
            else if (c.isAnnotationPresent(cz.zcu.kiv.formgen.annotation.MultiForm.class))
                loadClass(c, true);
            else
                throw new FormNotFoundException();
        }
    }

    
    @Override
    public void loadPackage(String name) throws FormNotFoundException {
        Reflections reflections = new Reflections(name);
        Set<Class<?>> classes;
        
        /* simple forms */
        classes = reflections.getTypesAnnotatedWith(cz.zcu.kiv.formgen.annotation.Form.class);
        for (Class<?> cls : classes) {
            loadClass(cls, false);
        }
        
        /* multiple (cross-class) forms */
        classes = reflections.getTypesAnnotatedWith(cz.zcu.kiv.formgen.annotation.MultiForm.class);
        for (Class<?> cls : classes) {
            loadClass(cls, true);
        }
    }


    @Override
    public void loadPackage(Package pack) throws FormNotFoundException {
        if (pack == null)
            return;
        
        // note: calling "new Reflections(Package);" can lead to 
        // "ReflectionsException: could not use param package ..."
        loadPackage(pack.getName());
    }
    
    
    @Override
    public Form getForm(String name) {
        return forms.get(name);
    }


    @Override
    public Collection<Form> getForms() {
        return forms.values();
    }
    
    
    
    /**
     * Loads the given class cls to the model. If the multiForm parameter is false, cls MUST
     * be annotated with the {@link cz.zcu.kiv.formgen.annotation.Form Form} annotation.
     * Otherwise (if multiForm is true), cls MUST be annotated with the
     * {@link cz.zcu.kiv.formgen.annotation.MultiForm MultiForm} annotation.
     * 
     * @param cls the class to be loaded
     * @param multiForm if true, the class is a part of a multi-form
     */
    private void loadClass(Class<?> cls, boolean multiForm) {
        if (multiForm) {
            cz.zcu.kiv.formgen.annotation.MultiForm annotation = 
                    cls.getAnnotation(cz.zcu.kiv.formgen.annotation.MultiForm.class);
            String name = annotation.value();
            if (forms.containsKey(name))
                parser.parse(cls, forms.get(name));
            else {
                Form form = parser.createMultiform(annotation);
                parser.parse(cls, form);
                forms.put(name, form);
            }
        } else {
            Form form = parser.parse(cls);
            forms.put(form.getFormName(), form);
        }
    }


}
