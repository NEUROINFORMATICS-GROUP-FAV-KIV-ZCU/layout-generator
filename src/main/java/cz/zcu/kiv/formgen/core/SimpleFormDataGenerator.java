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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import cz.zcu.kiv.formgen.FormDataGenerator;
import cz.zcu.kiv.formgen.FormNotFoundException;
import cz.zcu.kiv.formgen.ModelProvider;
import cz.zcu.kiv.formgen.model.Form;


/**
 *
 * @author Jakub Krauz
 */
public class SimpleFormDataGenerator implements FormDataGenerator {
    
    /** Object parser instance. */
    private ObjectParser parser;
    
    /** Map of created forms by their name. */
    private Map<String, Form> forms = new HashMap<String, Form>();
    
    
    
    /**
     * Constructs new generator using the given {@link ModelProvider} object.
     * @param modelProvider object that provides a concrete model implementation
     */
    public SimpleFormDataGenerator(ModelProvider modelProvider) {
        parser = new ObjectParser(modelProvider);
    }
    

    
    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.Generator#load(java.lang.Object[])
     */
    @Override
    public void load(Object... objects) throws FormNotFoundException {
        for (Object o : objects) {
            if (o.getClass().isAnnotationPresent(cz.zcu.kiv.formgen.annotation.Form.class))
                loadObject(o, false);
            else if (o.getClass().isAnnotationPresent(cz.zcu.kiv.formgen.annotation.MultiForm.class))
                loadObject(o, true);
            else
                throw new FormNotFoundException();
        }
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
     * Loads the given class cls to the model. If the multiForm parameter is false, cls MUST
     * be annotated with the {@link cz.zcu.kiv.formgen.annotation.Form Form} annotation.
     * Otherwise (if multiForm is true), cls MUST be annotated with the
     * {@link cz.zcu.kiv.formgen.annotation.MultiForm MultiForm} annotation.
     * 
     * @param obj the object to be loaded
     * @param multiForm if true, the object is a part of a multi-form
     */
    private void loadObject(Object obj, boolean multiForm) {
        if (multiForm) {
            cz.zcu.kiv.formgen.annotation.MultiForm annotation = 
                    obj.getClass().getAnnotation(cz.zcu.kiv.formgen.annotation.MultiForm.class);
            String name = annotation.value();
            if (forms.containsKey(name))
                parser.parse(obj, forms.get(name));
            else {
                Form form = parser.createMultiform(annotation);
                parser.parse(obj, form);
                forms.put(name, form);
            }
        } else {
            Form form = parser.parse(obj);
            forms.put(form.getFormName(), form);
        }
    }
    

}
