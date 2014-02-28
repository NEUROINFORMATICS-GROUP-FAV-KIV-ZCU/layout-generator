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
 * AbstractGenerator.java, 27. 2. 2014 13:12:30 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import cz.zcu.kiv.formgen.FormNotFoundException;
import cz.zcu.kiv.formgen.Generator;
import cz.zcu.kiv.formgen.model.Form;


/**
 * Abstract form generator.
 *
 * @author Jakub Krauz
 */
public abstract class AbstractGenerator<T> implements Generator<T> {
    
    
    /** The parser object. */
    protected AbstractParser<T> parser;
    
    /** Map of created forms by their name. */
    protected Map<String, Form> forms = new HashMap<String, Form>();
    
    
    
    /**
     * Constructs new generator that uses the given parser.
     * 
     * @param parser the parser object to be used
     */
    public AbstractGenerator(AbstractParser<T> parser) {
        this.parser = parser;
    }
    
    
    
    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.Generator#load(T...)
     */
    @Override
    public void load(T... objects) throws FormNotFoundException {
        for (T obj : objects) {
            if (annotation(obj, cz.zcu.kiv.formgen.annotation.Form.class) != null)
                load(obj, false);
            else if (annotation(obj, cz.zcu.kiv.formgen.annotation.MultiForm.class) != null)
                load(obj, true);
            else
                throw new FormNotFoundException();
        }
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
    
    
    
    /**
     * Extracts the given annotation from the object.
     * 
     * @param object the object
     * @param annotationClass class of the annotation to be extracted
     * @return extracted annotation or null
     */
    protected abstract <A extends Annotation> A annotation(T object, Class<A> annotationClass);
    
    
    
    /**
     * Loads the given class cls to the model. If the multiForm parameter is false, cls MUST
     * be annotated with the {@link cz.zcu.kiv.formgen.annotation.Form Form} annotation.
     * Otherwise (if multiForm is true), cls MUST be annotated with the
     * {@link cz.zcu.kiv.formgen.annotation.MultiForm MultiForm} annotation.
     * 
     * @param cls the class to be loaded
     * @param multiForm if true, the class is a part of a multi-form
     */
    protected void load(T object, boolean multiForm) {
        if (multiForm) {
            cz.zcu.kiv.formgen.annotation.MultiForm annotation = 
                    annotation(object, cz.zcu.kiv.formgen.annotation.MultiForm.class);
            String name = annotation.value();
            if (forms.containsKey(name))
                parser.parse(object, forms.get(name));
            else {
                Form form = parser.createMultiform(annotation);
                parser.parse(object, form);
                forms.put(name, form);
            }
        } else {
            Form form = parser.parse(object);
            forms.put(form.getName(), form);
        }
    }

}
