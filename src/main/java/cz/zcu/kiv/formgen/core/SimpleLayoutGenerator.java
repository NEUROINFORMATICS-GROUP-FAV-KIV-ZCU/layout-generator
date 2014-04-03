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

import java.lang.annotation.Annotation;
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
    
    /** The parser object. */
    private ClassParser parser = new ClassParser();
    
    /** Map of created forms by their name. */
    protected Map<String, Form> forms = new HashMap<String, Form>();

   
    
    
    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.Generator#load(T...)
     */
    @Override
    public void load(Class<?>... classes) throws FormNotFoundException {
        for (Class<?> cls : classes) {
            if (annotation(cls, cz.zcu.kiv.formgen.annotation.Form.class) != null)
                load(cls, false);
            else if (annotation(cls, cz.zcu.kiv.formgen.annotation.MultiForm.class) != null)
                load(cls, true);
            else
                throw new FormNotFoundException();
        }
    }
    
    
    
    @Override
    public void loadClass(String... names) throws ClassNotFoundException, FormNotFoundException {
        Class<?>[] classes = new Class<?>[names.length];
        for (int i = 0; i < classes.length; i++)
            classes[i] = Class.forName(names[i]);
        load(classes);
    }
    

    
    @Override
    public void loadPackage(String name) throws FormNotFoundException {
        Reflections reflections = new Reflections(name);
        Set<Class<?>> classes;
        
        /* simple forms */
        classes = reflections.getTypesAnnotatedWith(cz.zcu.kiv.formgen.annotation.Form.class);
        for (Class<?> cls : classes) {
            load(cls, false);
        }
        
        /* multiple (cross-class) forms */
        classes = reflections.getTypesAnnotatedWith(cz.zcu.kiv.formgen.annotation.MultiForm.class);
        for (Class<?> cls : classes) {
            load(cls, true);
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
    
    
    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.Generator#getForm(java.lang.String)
     */
    @Override
    public Form getForm(String name) {
        return forms.get(name);
    }


    
    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.Generator#getForms()
     */
    @Override
    public Collection<Form> getForms() {
        return forms.values();
    }


    private <A extends Annotation> A annotation(Class<?> cls, Class<A> annotationClass) {
        return cls.getAnnotation(annotationClass);
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
    protected void load(Class<?> cls, boolean multiForm) {
        if (multiForm) {
            cz.zcu.kiv.formgen.annotation.MultiForm annotation = 
                    annotation(cls, cz.zcu.kiv.formgen.annotation.MultiForm.class);
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
            forms.put(form.getName(), form);
        }
    }
    

}
