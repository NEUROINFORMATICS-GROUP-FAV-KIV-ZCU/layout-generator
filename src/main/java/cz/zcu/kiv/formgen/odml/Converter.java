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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cz.zcu.kiv.formgen.core.TypeMapper;
import cz.zcu.kiv.formgen.model.FieldDatatype;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormData;
import cz.zcu.kiv.formgen.model.FormDataField;
import cz.zcu.kiv.formgen.model.FormDataItem;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.model.FormItem;
import cz.zcu.kiv.formgen.model.FormItemContainer;
import cz.zcu.kiv.formgen.model.Type;
import cz.zcu.kiv.formgen.model.constraints.Constraint;
import cz.zcu.kiv.formgen.model.constraints.DefaultValue;
import cz.zcu.kiv.formgen.model.constraints.Length;
import cz.zcu.kiv.formgen.model.constraints.NumericalValue;
import cz.zcu.kiv.formgen.model.constraints.PossibleValues;
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
    
    
    /**
     * Converts from internal form-layout model to odML section tree.
     * 
     * @param form - the internal model to be converted
     * @return root section of odML tree
     * @throws OdmlConvertException 
     */
    public Section layoutToOdml(Form form) throws OdmlConvertException {
        Section section = null;
        
        try {
            
            section = new Section(form.getName(), Type.FORM.toString());

            if (form.getDataReference() != null)
                section.setReference(form.getDataReference());
            if (form.getDescription() != null)
                section.setDefinition(form.getDescription());
            if (form.getLabel() != null)
                section.addProperty("label", form.getLabel());
            if (form.getLayoutName() != null)
                section.addProperty("layoutName", form.getLayoutName());
            
            addItems(section, form.getItems());
            
        } catch (Exception e) {
            logger.error("Cannot create odML section.", e);
            throw new OdmlConvertException("Cannot create odML section.", e);
        }
        
        return section;
    }
    
    
    /**
     * Converts from internal form-layout model to odML section tree.
     * 
     * @param forms - the internal model to be converted
     * @return root section of the odML tree
     * @throws OdmlConvertException 
     */
    public Section layoutToOdml(Collection<Form> forms) throws OdmlConvertException {
        Section root = new Section();
        for (Form form : forms)
            root.add(layoutToOdml(form));
        return root;
    }
    
    
    /**
     * Converts from internal form-data model to odML section tree.
     * 
     * @param data - the internal model to be converted
     * @return root section of the odML tree
     * @throws OdmlConvertException 
     */
    public Section dataToOdml(FormData data) throws OdmlConvertException {
        Section section = null;
        
        try {
            section = new Section(data.getName(), data.getType());
            addItems(section, data.getItems());
        } catch (Exception e) {
            logger.error("Cannot create odML section.", e);
            throw new OdmlConvertException("Cannot create odML section.", e);
        }
        
        return section;
    }
    
    
    /**
     * Converts from internal form-data model to odML section tree.
     * 
     * @param data - the internal model to be converted
     * @return root section of the odML tree
     * @throws OdmlConvertException 
     */
    public Section dataToOdml(Collection<FormData> data) throws OdmlConvertException {
        Section root = new Section();
        for (FormData d : data)
            root.add(dataToOdml(d));
        return root;
    }
    
    
    /**
     * Converts from odML section tree to internal form-layout model.
     * 
     * @param section - the root section of the odML tree
     * @return internal form-layout model
     * @throws OdmlConvertException
     */
    public Form odmlToLayoutModel(Section section) throws OdmlConvertException {
        logger.debug("Converting form \"{}\"", section.getName());
        FormItem form = convertLayout(section);
        
        if (!(form instanceof Form))
            throw new OdmlConvertException("The root section must be of type \"form\"");
        else
            return (Form) form;
    }
    
    /**
     * Converts from odML section tree to internal form-data model.
     *  
     * @param section - the root section of odML tree
     * @return internal form model
     * @throws OdmlConvertException 
     */
    public Set<FormData> odmlToDataModel(Section odmlRoot) throws OdmlConvertException {
        Set<FormData> data = new HashSet<FormData>();
        
        if (odmlRoot.getSections() == null) {
            final String message = "The root odML section contains no subsections.";
            logger.error(message);
            throw new OdmlConvertException(message);
        }
        
        for (Section section : odmlRoot.getSections()) {
            logger.trace("Converting odML data: type=\"{}\", name=\"{}\"", section.getType(), section.getName());
            data.add(convertData(section));
        }
        
        return data;
    }
    
    
    
    
    
    
    /**
     * Converts the given section to a form item object.
     * 
     * @param section - the section to be converted
     * @return corresponding FormItem object
     */
    private FormItem convertLayout(Section section) {
        logger.trace("Converting section \"{}\"", section.getName());
        FormItem item = null;
        Property p;
        
        if (Type.FORM.toString().equals(section.getType())) {
            item = new Form(section.getName());
        } else {
            FormField field = new FormField(section.getName(), Type.fromString(section.getType()), 
                    FieldDatatype.fromString(section.getProperty("datatype").getText()));
            if ((p = section.getProperty("minLength")) != null)
                field.addConstraint(Length.MIN((Integer) p.getValue()));
            if ((p = section.getProperty("maxLength")) != null)
                field.addConstraint(Length.MAX((Integer) p.getValue()));
            if ((p = section.getProperty("minValue")) != null)
                field.addConstraint(NumericalValue.MIN((Number) p.getValue()));
            if ((p = section.getProperty("maxValue")) != null)
                field.addConstraint(NumericalValue.MAX((Number) p.getValue()));
            if ((p = section.getProperty("defaultValue")) != null)
                field.addConstraint(new DefaultValue(p.getValue()));
            if ((p = section.getProperty("values")) != null)
                field.addConstraint(new PossibleValues(p.getValues().toArray()));
            item = field;
        }
        
        if ((p = section.getProperty("id")) != null)
            item.setId((Integer) p.getValue());
        if ((p = section.getProperty("label")) != null)
            item.setLabel(p.getText());
        if ((p = section.getProperty("required")) != null)
            item.setRequired((Boolean) p.getValue());

        
        // subsections
        if ((item.getType() == Type.FORM || item.getType() == Type.SET)) {
            if (section.getSections() != null) {
                for (Section s : section.getSections())
                    ((FormItemContainer) item).addItem(convertLayout(s));
            }
        }
        
        return item;
    }
    
    
    private FormData convertData(Section section) {
        FormData data = new FormData(section.getType(), section.getName());
        
        for (Property property : section.getProperties()) {
            if (property.getValues().size() == 1) {
                FormDataField field = new FormDataField(property.getType(), property.getName());
                field.setValue(property.getValue());
                data.addItem((FormDataItem) field);
            } else {
                FormData set = new FormData(property.getType(), FormData.SET);
                for (Object value : property.getValues()) {
                    FormDataField field = new FormDataField(property.getType(), property.getName());
                    field.setValue(value);
                    set.addItem((FormDataItem) field);
                }
                data.addItem((FormDataItem) set); 
            }
        }
        
        if (section.getSections() != null) {
            for (Section subsection : section.getSections())
                data.addItem((FormDataItem) convertData(subsection));
        }
        
        return data;
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

        if (form.getDataReference() != null)
            section.setReference(form.getDataReference());
        if (form.getDescription() != null)
            section.setDefinition(form.getDescription());
        if (form.getId() > 0)
            section.addProperty("id", form.getId());
        if (form.getLabel() != null)
            section.addProperty("label", form.getLabel());
        section.addProperty("required", form.isRequired());
        if (form.getCardinality() != null)
            section.addProperty("cardinality", form.getCardinality().getValue());
        
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
        if (field.getCardinality() != null)
            section.addProperty("cardinality", field.getCardinality().getValue());
        section.addProperty("required", field.isRequired());
        section.addProperty("datatype", field.getDatatype().toString());
        
        // constraints
        for (Constraint constraint : field.getConstraints()) {
            if (constraint instanceof PossibleValues) {
                Property property = new Property("values");
                for (Object value : ((PossibleValues) constraint).getValues())
                    property.addValue(value);
                section.add(property);
            } else {
                section.addProperty(constraint.name(), constraint.value());
            }
        }
        
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
                if (lastId != -1)
                    subSection.addProperty("idTop", lastId);
                section.add(subSection);
            } else if (item instanceof FormField) {
                subSection = convert((FormField) item);
                if (lastId != -1)
                    subSection.addProperty("idTop", lastId);
                section.add(subSection);
            } else {
                logger.warn("Unknown form item type!");
            }
            lastId = item.getId();
        }
        
    }
    
    
    private void addItems(Section section, Collection<FormDataItem> items) throws Exception {
        for (FormDataItem item : items) {
            if (item instanceof FormDataField) {
                FormDataField field = (FormDataField) item;
                Object value = field.getValue();
                if (value == null)
                    continue;
                if (TypeMapper.isIntegerType(value.getClass()))
                    value = (Integer) ((Number) value).intValue();
                section.addProperty(field.getName(), value);
            } else if (item instanceof FormData) {
                FormData form = (FormData) item;
                Section subsection = new Section(form.getName(), form.getType());
                addItems(subsection, form.getItems());
                section.add(subsection);
            } else {
                logger.warn("Unknown form data item type!");
            }
        }
    }
    

}
