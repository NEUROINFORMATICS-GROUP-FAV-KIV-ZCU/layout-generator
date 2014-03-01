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
 * Converter.java, 28. 2. 2014 17:56:46 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml;

import java.util.Vector;
import cz.zcu.kiv.formgen.model.FieldType;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.model.FormItem;
import cz.zcu.kiv.formgen.model.FormSet;
import odml.core.Property;
import odml.core.Section;


/**
 *
 * @author Jakub Krauz
 */
public class Converter {
    
    private static final int COMBOBOX_MAX_ITEMS = 5;
    
    
    public Section layoutToOdml(Form form) {
        Section root = null;
        
        try {
            root = new Section(form.getName(), "form");
            if (form.getDescription() != null)
                root.setDefinition(form.getDescription());
            //root.addProperty("id", form.getId());
            if (form.getLabel() != null)
                root.addProperty("label", form.getLabel());
            
            addItems(root, form.getItems(), true);
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        if (form.getLayoutName() != null)
            root.addProperty("layoutName", form.getLayoutName());
        
        return root;
    }
    
    
    public Section dataToOdml(Form form) {
        Section root = null;
        
        try {
            root = new Section(form.getName(), "form");
            addItems(root, form.getItems(), false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return root;
    }
    
    
    
    private Section convert(Form form, boolean layoutInfo) {
        Section section = null;
        
        try {
            section = new Section(form.getName(), "form");
            
            if (layoutInfo) {
                if (form.getDescription() != null)
                    section.setDefinition(form.getDescription());
                if (form.getId() > 0)
                    section.addProperty("id", form.getId());
                if (form.getLabel() != null)
                    section.addProperty("label", form.getLabel());
                section.addProperty("required", form.isRequired());
            }
            
            addItems(section, form.getItems(), layoutInfo);
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return section;
    }
    
    
    private Section convert(FormField field) {
        Section section = null;
        
        try {
            section = new Section(field.getName(), field.getType().getValue());
            
            if (field.getId() > 0)
                section.addProperty("id", field.getId());
            if (field.getLabel() != null)
                section.addProperty("label", field.getLabel());
            section.addProperty("required", field.isRequired());
            section.addProperty("datatype", field.getDatatype().getValue());
            
            if (field.getMinLength() > 0)
                section.addProperty("minLength", field.getMinLength());
            if (field.getMaxLength() > 0)
                section.addProperty("maxLength", field.getMaxLength());
            if (field.getMinValue() != null)
                section.addProperty("minValue", field.getMinValue());
            if (field.getMaxValue() != null)
                section.addProperty("maxValue", field.getMaxValue());
            if (field.getDefaultValue() != null)
                section.addProperty("defaultValue", field.getDefaultValue());
            if (field.getPossibleValues() != null) {
                if (field.getPossibleValues().length <= COMBOBOX_MAX_ITEMS)   // TODO delat pri vytvareni FormField
                    section.setType(FieldType.COMBOBOX.getValue());
                else
                    section.setType(FieldType.CHOICE.getValue());
                Property property = new Property("values");
                for (Object value : field.getPossibleValues())
                    property.addValue(value);
                section.add(property);
            }
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return section;
    }
    
    
    
    private Section convert(FormSet set, boolean layoutInfo) {
        Section section = null;
        
        try {
            section = new Section(set.getName(), "set");
            
            if (layoutInfo) {
                if (set.getId() > 0)
                    section.addProperty("id", set.getId());
                if (set.getLabel() != null)
                    section.addProperty("label", set.getLabel());
                section.addProperty("required", set.isRequired());
            }
            
            addItems(section, set.getItems(), layoutInfo);
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return section;
    }
    
    
    
    private void addItems(Section section, Vector<FormItem> items, boolean layoutInfo) {
        int lastId = -1;
        Section subSection;
        
        for (FormItem item : items) {
            if (item instanceof Form) {
                subSection = convert((Form) item, layoutInfo);
                if (layoutInfo && lastId != -1)
                    subSection.addProperty("idTop", lastId);
                section.add(subSection);
            } else if (item instanceof FormSet) {
                subSection = convert((FormSet) item, layoutInfo);
                if (layoutInfo && lastId != -1)
                    subSection.addProperty("idTop", lastId);
                section.add(subSection);
            } else if (layoutInfo) {
                subSection = convert((FormField) item);
                if (lastId != -1)
                    subSection.addProperty("idTop", lastId);
                section.add(subSection);
            } else {
                section.addProperty(item.getName(), ((FormField) item).getValue());
            }
            
            if (layoutInfo)
                lastId = item.getId();
        }
        
    }
    
    

}
