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
import java.util.Set;
import org.reflections.Reflections;
import cz.zcu.kiv.formgen.LayoutGenerator;
import cz.zcu.kiv.formgen.FormNotFoundException;


/**
 * Generates form layout from a POJO data model.
 *
 * @author Jakub Krauz
 */
public class SimpleLayoutGenerator extends AbstractGenerator<Class<?>> implements LayoutGenerator {

    
    /**
     * Constructs new generator using the given {@link ModelProvider} object.
     * @param modelProvider object that provides a concrete model implementation
     */
    public SimpleLayoutGenerator() {
        super(new ClassParser());
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


    @Override
    protected <A extends Annotation> A annotation(Class<?> cls, Class<A> annotationClass) {
        return cls.getAnnotation(annotationClass);
    }
    

}
