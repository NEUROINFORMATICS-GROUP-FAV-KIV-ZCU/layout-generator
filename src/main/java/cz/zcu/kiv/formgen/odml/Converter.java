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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cz.zcu.kiv.formgen.core.TypeMapper;
import cz.zcu.kiv.formgen.model.FieldDatatype;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.model.FormItem;
import cz.zcu.kiv.formgen.model.FormItemContainer;
import cz.zcu.kiv.formgen.model.FormSet;
import cz.zcu.kiv.formgen.model.Type;
import odml.core.Property;
import odml.core.Section;


/**
 * Converts between internal form model and odML tree.
 *
 * @author Jakub Krauz
 */
public class Converter {
    
    /** Logger. */
    final Logger logger = LoggerFactory.getLogger(Converter.class);
    
    /** Determines whether the model contains layout definition (true) or data (false). */
    private boolean isLayout;
    
    
    /**
     * Converts from internal form model to odML section tree.
     * 
     * @param form - the internal model to be converted
     * @return root section of odML tree
     */
    public Section modelToOdml(Form form) {
        isLayout = form.isLayout();
        Section root = null;
        
        try {
            
            root = new Section(form.getName(), Type.FORM.toString());
            
            if (isLayout) {
                if (form.getDescription() != null)
                    root.setDefinition(form.getDescription());
                if (form.getLabel() != null)
                    root.addProperty("label", form.getLabel());
                if (form.getLayoutName() != null)
                    root.addProperty("layoutName", form.getLayoutName());
            }
            
            addItems(root, form.getItems());
            
        } catch (Exception e) {
            logger.error("Cannot create odML section.", e);
        }
        
        return root;
    }
    
    
    
    /**
     * Converts from odML section tree to internal form model.
     *  
     * @param section - the root section of odML tree
     * @return internal form model
     * @throws OdmlConvertException 
     */
    public Form odmlDataToModel(Section section) throws OdmlConvertException {
        Section formSection = section.getSection(0);  // root section wraps the form
        isLayout = formSection.getProperty("layoutName") != null;
        logger.debug("Converting form \"{}\" (isLayout = {})", formSection.getName(), isLayout);
        FormItem form = convert(formSection);
        
        if (!(form instanceof Form))
            throw new OdmlConvertException("The root section must be of type \"form\"");
        else
            return (Form) form;
    }
    
    
    
    /**
     * Converts the given section to a form item object.
     * 
     * @param section - the section to be converted
     * @return corresponding FormItem object
     */
    private FormItem convert(Section section) {
        logger.trace("Converting section \"{}\"", section.getName());
        FormItem item = null;
        Property p;
        
        if (Type.FORM.toString().equals(section.getType())) {
            item = new Form(section.getName());
        } else if (Type.SET.toString().equals(section.getType())) {
            item = new FormSet(section.getName(), null);
        } else if (isLayout) {
            FormField field = new FormField(section.getName(), Type.fromString(section.getType()), 
                    FieldDatatype.fromString(section.getProperty("datatype").getText()));
            if ((p = section.getProperty("minLength")) != null)
                field.setMinLength((Integer) p.getValue());
            if ((p = section.getProperty("maxLength")) != null)
                field.setMaxLength((Integer) p.getValue());
            if ((p = section.getProperty("minValue")) != null)
                field.setMinValue((Number) p.getValue());
            if ((p = section.getProperty("maxValue")) != null)
                field.setMaxValue((Number) p.getValue());
            if ((p = section.getProperty("defaultValue")) != null)
                field.setDefaultValue(p.getValue());
            if ((p = section.getProperty("values")) != null)
                field.setPossibleValues(p.getValues().toArray());
            item = field;
        } else {
            logger.warn("Bad section type.");
            return null;
        }
        
        if ((p = section.getProperty("id")) != null)
            item.setId((Integer) p.getValue());
        if ((p = section.getProperty("label")) != null)
            item.setLabel(p.getText());
        if ((p = section.getProperty("required")) != null)
            item.setRequired((Boolean) p.getValue());

        
        // subsections
        if ((item.getType() == Type.FORM || item.getType() == Type.SET)) {
            Vector<Section> subsections = section.getSections();
            if (subsections != null) {
                for (Section s : section.getSections())
                    ((FormItemContainer) item).addItem(convert(s));
            }
        }
        
        
        // data
        if (!isLayout && item.getType() == Type.FORM) {
            for (Property property : section.getProperties()) {
                if (property.getValues().size() == 1) {
                    FormField field = new FormField(property.getName());
                    field.setValue(property.getValue());
                    ((FormItemContainer) item).addItem(field);
                } else {
                    FormSet set = new FormSet(property.getName(), null);
                    int tmp = 1; 
                    for (Object value : property.getValues()) {
                        FormField field = new FormField("value_" + tmp++);
                        field.setValue(value);
                        set.addItem(field);
                    }
                    ((FormItemContainer) item).addItem(set);
                }
                
            }
        }
        
        return item;
    }
    
    

    /**
     * Converts the given Form to odML section.
     * 
     * @param form - the Form object to be converted
     * @return corresponding section object
     * @throws Exception if there was error when creating the section
     */
    private Section convert(Form form) throws Exception {
        Section section = new Section(form.getName(), Type.FORM.toString());
            
        if (isLayout) {
            if (form.getDescription() != null)
                section.setDefinition(form.getDescription());
            if (form.getId() > 0)
                section.addProperty("id", form.getId());
            if (form.getLabel() != null)
                section.addProperty("label", form.getLabel());
            section.addProperty("required", form.isRequired());
        }
        
        addItems(section, form.getItems());
        
        return section;
    }
    
    
    
    /**
     * Converts the given FormField to odML section.
     * 
     * @param field - the FormField object to be converted
     * @return corresponding section object
     * @throws Exception if there was error when creating the section
     */
    private Section convert(FormField field) throws Exception {
        Section section = new Section(field.getName(), field.getType().toString());
        
        if (field.getId() > 0)
            section.addProperty("id", field.getId());
        if (field.getLabel() != null)
            section.addProperty("label", field.getLabel());
        section.addProperty("required", field.isRequired());
        section.addProperty("datatype", field.getDatatype().toString());
        
        // restrictions
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
            Property property = new Property("values");
            for (Object value : field.getPossibleValues())
                property.addValue(value);
            section.add(property);
        }
        
        return section;
    }
    
    
    
    /**
     * Converts the given FormSet object to odML section.
     * 
     * @param set - the FormSet to be converted
     * @return corresponding odML section
     * @throws Exception if there was error when creating the section
     */
    private Section convert(FormSet set) throws Exception {
        Section section = new Section(set.getName(), Type.SET.toString());
            
        if (isLayout) {
            if (set.getId() > 0)
                section.addProperty("id", set.getId());
            if (set.getLabel() != null)
                section.addProperty("label", set.getLabel());
            section.addProperty("required", set.isRequired());
        }
        
        addItems(section, set.getItems());
        
        return section;
    }
    
    
    
    /**
     * Converts and adds the given form items to the section object.
     * 
     * @param section - the section
     * @param items - items to be converted and added in the section
     * @throws Exception if there was error when creating the section
     */
    private void addItems(Section section, Vector<FormItem> items) throws Exception {
        int lastId = -1;
        Section subSection;
        
        for (FormItem item : items) {
            if (item instanceof Form) {
                subSection = convert((Form) item);
                if (isLayout && lastId != -1)
                    subSection.addProperty("idTop", lastId);
                section.add(subSection);
            } else if (item instanceof FormSet) {
                subSection = convert((FormSet) item);
                if (isLayout && lastId != -1)
                    subSection.addProperty("idTop", lastId);
                section.add(subSection);
            } else if (isLayout) {
                subSection = convert((FormField) item);
                if (lastId != -1)
                    subSection.addProperty("idTop", lastId);
                section.add(subSection);
            } else {
                Object value = ((FormField) item).getValue();
                if (value == null)
                    continue;
                if (TypeMapper.isIntegerType(value.getClass()))  // odML uses only "int" type
                    value = ((Number) value).intValue();
                section.addProperty(item.getName(), value);
            }
            
            if (isLayout)
                lastId = item.getId();
        }
        
    } 
    

}
