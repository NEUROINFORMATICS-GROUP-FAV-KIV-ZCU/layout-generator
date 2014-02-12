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
 * OdmlTypeMapper.java, 16. 12. 2013 13:37:27 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml;

import java.util.HashMap;
import java.util.Map;
import cz.zcu.kiv.formgen.TypeMapper;
import cz.zcu.kiv.formgen.core.AbstractTypeMapper;


/**
 *
 * @author Jakub Krauz
 */
public class OdmlTypeMapper extends AbstractTypeMapper implements TypeMapper {
        
    private static final Map<Class<?>, SectionType> TYPES = new HashMap<Class<?>, SectionType>();
    
    private static final Map<Class<?>, FormItemDatatype> DATATYPES = new HashMap<Class<?>, FormItemDatatype>();
    
    private static OdmlTypeMapper instance;
    
    
    static {
        TYPES.put(Boolean.TYPE, SectionType.CHECKBOX);
        TYPES.put(Byte.TYPE, SectionType.TEXTBOX);
        TYPES.put(Character.TYPE, SectionType.TEXTBOX);
        TYPES.put(Short.TYPE, SectionType.TEXTBOX);
        TYPES.put(Integer.TYPE, SectionType.TEXTBOX);
        TYPES.put(Long.TYPE, SectionType.TEXTBOX);
        TYPES.put(Float.TYPE, SectionType.TEXTBOX);
        TYPES.put(Double.TYPE, SectionType.TEXTBOX);
        TYPES.put(String.class, SectionType.TEXTBOX);
        TYPES.put(java.util.Date.class, SectionType.TEXTBOX);
        TYPES.put(java.sql.Date.class, SectionType.TEXTBOX);
        TYPES.put(java.util.Collection.class, SectionType.SET);
    }
    
    
    static {
        DATATYPES.put(Byte.TYPE, FormItemDatatype.INTEGER);
        DATATYPES.put(Character.TYPE, FormItemDatatype.STRING);
        DATATYPES.put(Short.TYPE, FormItemDatatype.INTEGER);
        DATATYPES.put(Integer.TYPE, FormItemDatatype.INTEGER);
        DATATYPES.put(Long.TYPE, FormItemDatatype.INTEGER);
        DATATYPES.put(Float.TYPE, FormItemDatatype.NUMBER);
        DATATYPES.put(Double.TYPE, FormItemDatatype.NUMBER);
        DATATYPES.put(String.class, FormItemDatatype.STRING);
        DATATYPES.put(java.util.Date.class, FormItemDatatype.DATE);
        DATATYPES.put(java.sql.Date.class, FormItemDatatype.DATE);
    }
    
    
    public static OdmlTypeMapper instance() {
        if (instance == null)
            instance = new OdmlTypeMapper();
        return instance;
    }
    
    
    private OdmlTypeMapper() {
        // private ctor
    }

    
    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.TypeMapper#isSimpleType(java.lang.Class)
     */
    @Override
    public boolean isSimpleType(Class<?> type) {        
        return type.isPrimitive() || isWrapperType(type) || TYPES.containsKey(type) || TYPES.containsKey(type.getSuperclass());
    }


    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.TypeMapper#mapType(java.lang.Class)
     */
    @Override
    public String mapType(Class<?> type) {
        if (isWrapperType(type))
            type = toPrimitiveType(type);
        
        if (TYPES.containsKey(type))
            return TYPES.get(type).getValue();
        
        for (Class<?> cls : TYPES.keySet()) {
            if (cls.isAssignableFrom(type))
                return TYPES.get(cls).getValue();
        }
            
        // default
        return SectionType.TEXTBOX.getValue();
    }
    
    
    
    public String mapDatatype(Class<?> type) {
        if (isWrapperType(type))
            type = toPrimitiveType(type);
        
        if (DATATYPES.containsKey(type))
            return DATATYPES.get(type).getValue();

        // check if the type is a subclass of mapped types
        for (Class<?> cls : DATATYPES.keySet()) {
            if (cls.isAssignableFrom(type))
                return DATATYPES.get(cls).getValue();
        }
        
        // default
        return FormItemDatatype.STRING.getValue();
    }


}
