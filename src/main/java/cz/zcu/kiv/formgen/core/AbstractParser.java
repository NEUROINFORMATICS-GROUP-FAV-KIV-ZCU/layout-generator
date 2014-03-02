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
import cz.zcu.kiv.formgen.model.Form;


/**
 *
 * @author Jakub Krauz
 */
public abstract class AbstractParser<T> {
    
    
    /**
     * Parses the given object and creates a new {@link Form} model.
     * 
     * @param obj - the object to be parsed
     * @return the newly created {@link Form} object
     */
    public abstract Form parse(T obj);
    
    
    /**
     * Parses the given object and adds it as a subform to an existing {@link Form} model
     * (used for multiple-class forms).
     * 
     * @param obj - the object to be parsed
     * @param form - the form in which the parsed class is to be added
     */
    public abstract void parse(T obj, Form form);
    

    
    /**
     * Creates a multi-form object defined by the annotation.
     * 
     * @param annotation the {@link cz.zcu.kiv.formgen.annotation.MultiForm MultiForm} annotation
     * @return new form defined by the annotation
     */
    public Form createMultiform(cz.zcu.kiv.formgen.annotation.MultiForm annotation) {
        return new Form(annotation.value());
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
        return TypeMapper.instance().isSimpleType(type);
    }
    
    
    
    /**
     * Determines whether the given type is a whole-number type (i.e. byte, short, int, long or their object wrappers).
     * 
     * @param type the Java type
     * @return true if the type is a whole-number type, false otherwise
     */
    protected boolean isIntegerType(Class<?> type) {
        Class<?> cls = type.isPrimitive() ? type : TypeMapper.toPrimitiveType(type);
        return (cls == byte.class || cls == short.class || cls == int.class || cls == long.class);
    }
    
    
    
    /**
     * Returns collection of fields annotated by the {@link cz.zcu.kiv.formgen.annotation.FormItem FormItem} annotation.
     * 
     * @param cls - the class
     * @return collection of annotated fields
     */
    protected Collection<Field> formItemFields(Class<?> cls) {
        Vector<Field> vector = new Vector<Field>();
        
        for (Field f : cls.getDeclaredFields()) {
            if (f.isAnnotationPresent(cz.zcu.kiv.formgen.annotation.FormItem.class))
                vector.add(f);
        }
        
        return vector;
    }

}
