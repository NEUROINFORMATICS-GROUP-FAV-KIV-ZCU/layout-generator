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
 * AbstractParser.java, 26. 2. 2014 10:34:34 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Vector;
import cz.zcu.kiv.formgen.ModelProvider;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormField;


/**
 *
 * @author Jakub Krauz
 */
public abstract class AbstractParser<T> {
    
    
    /** Object used to create new {@link Form} and {@link FormField} objects. */
    protected ModelProvider formProvider;
    
    
    public AbstractParser(ModelProvider formProvider) {
        this.formProvider = formProvider;
    }
    
    
    public Form parse(T obj) {
        return _parse(obj);
    }
    
    
    public void parse(T obj, Form form) {
        form.addItem(_parse(obj));
    }
    
    
    protected abstract Form _parse(T obj);

    
    /**
     * Creates a multi-form object defined by the annotation.
     * 
     * @param annotation the {@link cz.zcu.kiv.formgen.annotation.MultiForm MultiForm} annotation
     * @return new form defined by the annotation
     */
    public Form createMultiform(cz.zcu.kiv.formgen.annotation.MultiForm annotation) {
        return formProvider.newForm(annotation.value());
    }
    
    
    
    /**
     * Creates a new {@link Form} representing the given class using the {@link ModelProvider} object. 
     * The provider object is set in the constructor (see {@link #ClassParser(ModelProvider)}.
     * 
     * @param cls the Java class representing the form to be created
     * @return the newly created form
     */
    protected Form createForm(Class<?> cls) {
        String name;
        if (cls.isAnnotationPresent(cz.zcu.kiv.formgen.annotation.Form.class)) {
            name = cls.getAnnotation(cz.zcu.kiv.formgen.annotation.Form.class).value();
            if (name.isEmpty())
                name = cls.getSimpleName();
        } else {
            name = cls.getSimpleName();
        }
        
        return formProvider.newForm(name);
    }
    
    
    
    /**
     * Determines whether the given type is considered a simple type in the given model using
     * the {@link ModelProvider} object. The provider object is set in the constructor 
     * (see {@link #ClassParser(ModelProvider)}.
     * 
     * @param type the Java type
     * @return true if the type is considered simple in the given model, false otherwise
     */
    protected boolean isSimpleType(Class<?> type) {
        return formProvider.typeMapper().isSimpleType(type);
    }
    
    
    
    /**
     * Determines whether the given type is a whole-number type (i.e. byte, short, int, long or their object wrappers).
     * 
     * @param type the Java type
     * @return true if the type is a whole-number type, false otherwise
     */
    protected boolean isIntegerType(Class<?> type) {
        Class<?> cls = type.isPrimitive() ? type : AbstractTypeMapper.toPrimitiveType(type);
        return (cls == byte.class || cls == short.class || cls == int.class || cls == long.class);
    }
    
    
    
    protected Collection<Field> formItemFields(Class<?> cls) {
        Vector<Field> vector = new Vector<Field>();
        
        for (Field f : cls.getDeclaredFields()) {
            if (f.isAnnotationPresent(cz.zcu.kiv.formgen.annotation.FormItem.class))
                vector.add(f);
        }
        
        return vector;
    }

}
