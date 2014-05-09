/***********************************************************************************************************************
 *
 * This file is part of the layout-generator project
 *
 * =================================================
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
import cz.zcu.kiv.formgen.model.Type;
import cz.zcu.kiv.formgen.model.constraints.Constraint;
import cz.zcu.kiv.formgen.model.constraints.DefaultValue;
import cz.zcu.kiv.formgen.model.constraints.Length;
import cz.zcu.kiv.formgen.model.constraints.NumericalValue;
import cz.zcu.kiv.formgen.model.constraints.PossibleValues;
import odml.core.Property;
import odml.core.Section;


/**
 * Converts between internal model and corresponding odML tree.
 *
 * @author Jakub Krauz
 */
public class Converter {
    
    /** Logger. */
    final Logger logger = LoggerFactory.getLogger(Converter.class);
    
    
    /**
     * Converts the internal {@link Form} model to odML section tree.
     * 
     * @param form The internal model to be converted.
     * @return corresponding odML section tree
     * @throws OdmlConvertException If an error occurs during the conversion.
     */
    public Section layoutToOdml(Form form) throws OdmlConvertException {
        if (form == null)
            return null;
        
        try {
            
            Section section = new Section(form.getName(), Type.FORM.toString());

            if (form.getDataReference() != null)
                section.setReference(form.getDataReference());
            if (form.getDescription() != null)
                section.setDefinition(form.getDescription());
            if (form.getLabel() != null)
                section.addProperty("label", form.getLabel());
            if (form.getLayoutName() != null)
                section.addProperty("layoutName", form.getLayoutName());
            if (form.getMajorPreviewField() != null)
                section.addProperty("previewMajor", form.getMajorPreviewField().getName());
            if (form.getMinorPreviewField() != null)
                section.addProperty("previewMinor", form.getMinorPreviewField().getName());
            
            addItems(section, form.getItems());
            
            return section;
            
        } catch (Exception e) {
            logger.error("Cannot create odML section.", e);
            throw new OdmlConvertException("Cannot create odML section.", e);
        }
        
    }
    
    
    /**
     * Converts the internal {@link Form} model to odML section tree.
     * 
     * @param forms The internal model to be converted.
     * @return corresponding odML tree
     * @throws OdmlConvertException If an error occurs during the conversion.
     */
    public Section layoutToOdml(Collection<Form> forms) throws OdmlConvertException {
        if (forms == null)
            return null;
        
        Section root = new Section();
        for (Form form : forms)
            root.add(layoutToOdml(form));
        return root;
    }
    
    
    /**
     * Converts the internal {@link FormData} model to odML section tree.
     * 
     * @param data The internal model to be converted.
     * @return corresponding odML tree
     * @throws OdmlConvertException If an error occurs during the conversion.
     */
    public Section dataToOdml(FormData data) throws OdmlConvertException {
        if (data == null)
            return null;
        
        try {
            Section section = new Section(data.getName(), data.getType());
            if (data.getId() != null)
                section.addProperty("id", data.getId().toString());
            addItems(section, data.getItems());
            return section;
        } catch (Exception e) {
            logger.error("Cannot create odML section.", e);
            throw new OdmlConvertException("Cannot create odML section.", e);
        }
        
    }
    

    /**
     * Converts the internal {@link FormData} model to odML section tree.
     * 
     * @param data The internal model to be converted.
     * @return corresponding odML tree
     * @throws OdmlConvertException If an error occurs during the conversion.
     */
    public Section dataToOdml(Collection<FormData> data) throws OdmlConvertException {
        if (data == null)
            return null;
        
        Section root = new Section();
        for (FormData d : data)
            root.add(dataToOdml(d));
        return root;
    }
    
    
    /**
     * Converts odML section tree to internal {@link Form} model.
     * 
     * @param section The root section of the odML tree.
     * @return corresponding form-layout model
     * @throws OdmlConvertException If an error occurs during the conversion.
     */
    public Form odmlToLayoutModel(Section section) throws OdmlConvertException {
        if (section == null)
            return null;
        
        logger.debug("Converting form \"{}\"", section.getName());
        FormItem form = convertLayout(section);
        
        if (!(form instanceof Form))
            throw new OdmlConvertException("The root section must be of type \"form\"");
        else
            return (Form) form;
    }
    
    /**
     * Converts odML section tree to internal {@link FormData} model.
     *  
     * @param odmlRoot The root section of odML tree.
     * @return corresponding form-data model
     * @throws OdmlConvertException If an error occurs during the conversion.
     */
    public Set<FormData> odmlToDataModel(Section odmlRoot) throws OdmlConvertException {
        if (odmlRoot == null)
            return null;
        
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
     * @param section The section to be converted.
     * @return corresponding form item
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
        if (item instanceof Form) {
            if (section.getSections() != null) {
                for (Section s : section.getSections())
                    ((Form) item).addItem(convertLayout(s));
            }
        }
        
        return item;
    }
    
    
    /**
     * Converts the given odML section to a form data object.
     * @param section The section to be converted.
     * @return corresponding form data object
     */
    private FormData convertData(Section section) {
        FormData data = new FormData(section.getType(), section.getName());
        
        for (Property property : section.getProperties()) {
            if (property.getName().equals("id")) {
                data.setId(property.getValue());
            } else if (property.getValues().size() == 1) {
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
     * Converts the given form to an odML section.
     * 
     * @param form The form to be converted.
     * @return corresponding odML section
     * @throws Exception If there was error creating the section.
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
        if (form.getMajorPreviewField() != null)
            section.addProperty("previewMajor", form.getMajorPreviewField().getName());
        if (form.getMinorPreviewField() != null)
            section.addProperty("previewMinor", form.getMinorPreviewField().getName());
        
        addItems(section, form.getItems());
        
        return section;
    }
    
    
    
    /**
     * Converts the given form field to odML section.
     * 
     * @param field The form field to be converted.
     * @return corresponding odML section
     * @throws Exception If there was error creating the section.
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
     * Converts the given vector of form items to odML objects and adds them to the section.
     * 
     * @param section The section.
     * @param items Items to be converted and added to the section.
     * @throws Exception If there was error creating an odML section.
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
    
    
    /**
     * Converts the given collection of data items to odML objects and adds them to the section.
     * 
     * @param section The section.
     * @param items Collection of data items.
     * @throws Exception If there was error creating an odML section.
     */
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
                if (form.getId() != null)
                    subsection.addProperty("id", form.getId().toString());
                addItems(subsection, form.getItems());
                section.add(subsection);
            } else {
                logger.warn("Unknown form data item type!");
            }
        }
    }
    

}
