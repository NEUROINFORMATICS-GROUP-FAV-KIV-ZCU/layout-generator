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
 * OdmlGuiConverter.java, 26. 9. 2014 13:41:46 Jakub Krauz
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
import cz.zcu.kiv.formgen.model.constraints.Constraint;
import cz.zcu.kiv.formgen.model.constraints.DefaultValue;
import cz.zcu.kiv.formgen.model.constraints.Length;
import cz.zcu.kiv.formgen.model.constraints.NumericalValue;
import cz.zcu.kiv.formgen.model.constraints.PossibleValues;
import odml.core.GUIHelper;
import odml.core.Property;
import odml.core.Section;


/**
 * Converts between internal model and corresponding odML tree.
 *
 * @author Jakub Krauz
 */
public class OdmlGuiConverter implements Converter {
    
    /** Logger. */
    final Logger logger = LoggerFactory.getLogger(OdmlGuiConverter.class);
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Section layoutToOdml(Form form) throws OdmlConvertException {
        if (form == null)
            return null;
        
        try {
        	return convert(form);
        } catch (Exception e) {
            String desc = "Cannot create odML section.";
            logger.error(desc, e);
            throw new OdmlConvertException(desc, e);
        }
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Section layoutToOdml(Collection<Form> forms) throws OdmlConvertException {
        if (forms == null)
            return null;
        
        Section root = new Section();
        for (Form form : forms)
            root.add(layoutToOdml(form));
        return root;
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Section dataToOdml(FormData data) throws OdmlConvertException {
        if (data == null)
            return null;
        
        try {
            Section section = new Section(data.getLabel(), data.getType());
            section.setReference(data.getName());
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
     * {@inheritDoc}
     */
    @Override
    public Section dataToOdml(Collection<FormData> data) throws OdmlConvertException {
        if (data == null)
            return null;
        
        Section root = new Section();
        for (FormData d : data)
            root.add(dataToOdml(d));
        return root;
    }
    
    
    /**
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
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
     * Converts the given section to a form object.
     * 
     * @param section The section to be converted.
     * @return corresponding form item
     */
    private Form convertLayout(Section section) throws OdmlConvertException {
        logger.trace("Converting section \"{}\"", section.getName());
        if (section.getReference() == null || section.getReference().isEmpty())
        	throw new OdmlConvertException("Cannot convert section, missing reference to data entity.");
        String[] ref = section.getReference().split(":");
        if (ref.length != 2)
        	throw new OdmlConvertException("Cannot convert section, the reference to data entity is invalid.");
        Form form = new Form(ref[0]);
        form.setDataReference(ref[1]);
        form.setLabel(section.getName());
        
        Property p;
        if ((p = section.getProperty("id")) != null)
            form.setId((Integer) p.getValue());

        // subsections
        if (section.getSections() != null) {
            for (Section s : section.getSections())
                form.addItem(convertLayout(s));
        }
        
        // properties
        if (section.getProperties() != null) {
        	for (Property prop : section.getProperties())
        		form.addItem(convertLayout(prop));
        }
        
        return form;
    }
    
    
    /**
     * Converts the given property to a corresponding field object.
     * 
     * @param property The property to be converted.
     * @return corresponding form field
     */
    private FormField convertLayout(Property property) {
        logger.trace("Converting property \"{}\"", property.getName());
        GUIHelper gui = property.getGuiHelper();
        FormField field = new FormField(gui.getReference());
        field.setLabel(property.getName());
        field.setRequired(gui.getRequired());
        if (gui.getDatatype() != null)
        	field.setDatatype(FieldDatatype.fromString(gui.getDatatype()));
    	if (gui.getMinLength() != null)
            field.addConstraint(Length.MIN(gui.getMinLength()));
        if (gui.getMaxLength() != null)
            field.addConstraint(Length.MAX((Integer) gui.getMaxLength()));
        if (gui.getMinValue() != null)
            field.addConstraint(NumericalValue.MIN((Number) gui.getMinValue()));
        if (gui.getMaxValue() != null)
            field.addConstraint(NumericalValue.MAX((Number) gui.getMaxValue()));
        if (gui.getDefaultValue() != null)
            field.addConstraint(new DefaultValue(gui.getDefaultValue()));
        if (gui.getList() != null)
            field.addConstraint(new PossibleValues(gui.getList().split(";")));

        return field;
    }
    
    
    /**
     * Converts the given odML section to a form data object.
     * @param section The section to be converted.
     * @return corresponding form data object
     */
    private FormData convertData(Section section) {
        FormData data = new FormData(section.getType(), section.getReference().split(":")[0]);
        data.setLabel(section.getName());
        
        for (Property property : section.getProperties()) {
            if (property.getName().equals("id")) {
                data.setId(property.getValue());
            } else if (property.getValues().size() == 1) {
                FormDataField field = new FormDataField(property.getGuiHelper().getReference());
                field.setLabel(property.getName());
                field.setValue(property.getValue());
                data.addItem((FormDataItem) field);
            } else {
                FormData set = new FormData(property.getType(), FormData.SET);
                for (Object value : property.getValues()) {
                    FormDataField field = new FormDataField(property.getGuiHelper().getReference());
                    field.setLabel(property.getName());
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
    	String ref = form.getDataReference();
    	String type = ref.substring(ref.lastIndexOf('.') + 1);
        Section section = new Section(form.getLabel(), type);
        section.setReference(form.getName() + ":" + ref);

        if (form.getDescription() != null)
            section.setDefinition(form.getDescription());
        
        addItems(section, form.getItems());
        
        return section;
    }
    
    
    
    /**
     * Converts the given form field to odML property.
     * 
     * @param field The form field to be converted.
     * @return corresponding odML property
     * @throws Exception If there was error creating the section.
     */
    private Property convert(FormField field) throws Exception {
    	Property property = new Property(field.getLabel(), null, field.getDatatype().toString());
    	GUIHelper gui = property.getGuiHelper();
    	gui.setReference(field.getName());
    	gui.setRequired(field.isRequired());
    	gui.setDatatype(field.getDatatype().toString());
    	
    	for (Constraint constraint : field.getConstraints()) {
            if (constraint instanceof Length) {
            	Length length = (Length) constraint;
            	if (length.name().equals("maxLength")) {
            		gui.setMaxLength((Integer) length.value());
            	} else {
            		gui.setMinLength((Integer) length.value());
            	}
            } else if (constraint instanceof NumericalValue) {
                NumericalValue val = (NumericalValue) constraint;
                if (val.name().equals("maxValue")) {
            		gui.setMaxValue(((Number) val.value()).doubleValue());
            	} else {
            		gui.setMinValue(((Number) val.value()).doubleValue());
            	}
            } else if (constraint instanceof DefaultValue) {
            	gui.setDefaultValue(constraint.value().toString());
            } else if (constraint instanceof PossibleValues) {
            	StringBuffer buf = new StringBuffer();
            	for (Object value : ((PossibleValues) constraint).getValues())
            		buf.append(";" + value.toString());
            	gui.setList(buf.substring(1));  // remove the first ';'
            }
        }
        
        return property;
    }
    
    
    
    /**
     * Converts the given vector of form items to odML objects and adds them to the section.
     * 
     * @param section The section.
     * @param items Items to be converted and added to the section.
     * @throws Exception If there was error creating an odML section.
     */
    private void addItems(Section section, Vector<FormItem> items) throws Exception {
        //int order = 1;  // order is now added automatically by the odml-java-lib
        
        for (FormItem item : items) {
            if (item instanceof Form) {
            	Section subSection = convert((Form) item);
            	//subSection.getGuiHelper().setOrder(order++);
                section.add(subSection);
            } else if (item instanceof FormField) {
            	Property property = convert((FormField) item);
            	//property.getGuiHelper().setOrder(Integer.valueOf(order++));
            	section.add(property);
            } else {
                logger.warn("Unknown form item type!");
            }
        }
        
    }
    
    
    /**
     * Converts the given collection of data items to odML objects
     * and adds them to the section.
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
                Property property = new Property(field.getLabel(), value);
                property.getGuiHelper().setReference(field.getName());
                section.add(property);
            } else if (item instanceof FormData) {
                FormData form = (FormData) item;
                Section subsection = new Section(form.getLabel(), form.getType());
                subsection.setReference(form.getName());
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
