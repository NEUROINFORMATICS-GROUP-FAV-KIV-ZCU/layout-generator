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
 * SimpleLayoutGenerator.java, 15. 11. 2013 17:36:16 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import cz.zcu.kiv.formgen.LayoutGenerator;
import cz.zcu.kiv.formgen.FormNotFoundException;
import cz.zcu.kiv.formgen.annotation.MultiForm;
import cz.zcu.kiv.formgen.model.Form;


/**
 * Controls the process of form layouts generation.
 *
 * @author Jakub Krauz
 */
public class SimpleLayoutGenerator implements LayoutGenerator {
    
    /** The parser object. */
    private ClassParser parser = new ClassParser();
    
    /** Map of created forms by their name. */
    protected Map<String, Form> forms = new HashMap<String, Form>();
    
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Form load(Class<?> cls) throws FormNotFoundException {
        if (cls.getAnnotation(cz.zcu.kiv.formgen.annotation.Form.class) != null)
            return load(cls, false);
        else if (cls.getAnnotation(MultiForm.class) != null)
            return load(cls, true);
        else
            throw new FormNotFoundException();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Form> load(Collection<Class<?>> classes) throws FormNotFoundException {
        Collection<Form> loaded = new HashSet<Form>(classes.size());
        for (Class<?> cls : classes)
            loaded.add(load(cls));
        return loaded;
    }
    

    /**
     * {@inheritDoc}
     */
    @Override
    public Form loadClass(String name) throws ClassNotFoundException, FormNotFoundException {
        return load(Class.forName(name));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Form> loadClasses(String[] names) throws ClassNotFoundException, FormNotFoundException {
        Collection<Class<?>> classes = new HashSet<Class<?>>(names.length);
        for (int i = 0; i < names.length; i++)
            classes.add(Class.forName(names[i]));
        return load(classes);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Form> loadClasses(Collection<String> names) throws ClassNotFoundException, FormNotFoundException {
        Collection<Class<?>> classes = new HashSet<Class<?>>(names.size());
        for (String name : names)
            classes.add(Class.forName(name));
        return load(classes);
    }
    

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Form> loadPackage(String name) throws FormNotFoundException {
        Collection<Form> loaded;
        Reflections reflections = new Reflections(name);
        Set<Class<?>> classes;
        
        /* simple forms */
        classes = reflections.getTypesAnnotatedWith(cz.zcu.kiv.formgen.annotation.Form.class);
        loaded = new HashSet<Form>(classes.size());
        for (Class<?> cls : classes) {
            loaded.add(load(cls, false));
        }
        
        /* multiple (cross-class) forms */
        classes = reflections.getTypesAnnotatedWith(cz.zcu.kiv.formgen.annotation.MultiForm.class);
        for (Class<?> cls : classes) {
            loaded.add(load(cls, true));
        }
        
        return loaded;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Form> loadPackage(Package pack) throws FormNotFoundException {
        if (pack == null)
            return null;
        
        // note: calling "new Reflections(Package);" can lead to 
        // "ReflectionsException: could not use param package ..."
        return loadPackage(pack.getName());
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Form> getLoadedModel() {
        return forms.values();
    }
    

    /**
     * {@inheritDoc}
     */
    @Override
    public Form getLoadedForm(String name) {
        return forms.get(name);
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void clearModel() {
        forms.clear();
    }
    
    
    /**
     * Loads the given class cls to the model. If the multiForm parameter is false, cls MUST
     * be annotated with the {@link cz.zcu.kiv.formgen.annotation.Form Form} annotation.
     * Otherwise (if multiForm is true), cls MUST be annotated with the
     * {@link cz.zcu.kiv.formgen.annotation.MultiForm MultiForm} annotation.
     * 
     * @param cls The class to be loaded.
     * @param multiForm If true, the class is a part of a multi-form.
     */
    protected Form load(Class<?> cls, boolean multiForm) {
        Form form;
        
        if (multiForm) {
            cz.zcu.kiv.formgen.annotation.MultiForm annotation = 
                    cls.getAnnotation(cz.zcu.kiv.formgen.annotation.MultiForm.class);
            String name = annotation.value();
            if (forms.containsKey(name)) {
                form = forms.get(name);
                parser.parse(cls, form);
            } else {
                form = parser.createMultiform(annotation);
                parser.parse(cls, form);
                forms.put(name, form);
            }
        } else {
            form = parser.parse(cls);
            forms.put(form.getName(), form);
        }
        
        return form;
    }

}
