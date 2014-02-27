/***********************************************************************************************************************
 *
 * This file is part of the layout-generator project
 *
 * ==========================================
 *
 * Copyright (C) 2014 by University of West Bohemia (http://www.zcu.cz/en/)
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
 * SimpleFormDataGenerator.java, 27. 2. 2014 12:44:53 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import java.lang.annotation.Annotation;
import java.util.Collection;
import cz.zcu.kiv.formgen.FormDataGenerator;
import cz.zcu.kiv.formgen.FormNotFoundException;
import cz.zcu.kiv.formgen.ModelProvider;


/**
 * Generates form with data from the given object model.
 *
 * @author Jakub Krauz
 */
public class SimpleFormDataGenerator extends AbstractGenerator<Object> implements FormDataGenerator {

    
    /**
     * Constructs new generator using the given {@link ModelProvider} object.
     * @param modelProvider object that provides a concrete model implementation
     */
    public SimpleFormDataGenerator(ModelProvider modelProvider) {
        super(new ObjectParser(modelProvider));
    }
    

    
    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.FormDataGenerator#loadObjects(java.util.Collection<java.lang.Object>)
     */
    @Override
    public void loadObjects(Collection<Object> collection) throws FormNotFoundException {
        for (Object object : collection)
            load(object);
    }



    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.core.AbstractGenerator(Object, Class<A extends Annotation>)
     */
    @Override
    protected <A extends Annotation> A annotation(Object object, Class<A> annotationClass) {
        return object.getClass().getAnnotation(annotationClass);
    }
    

}
