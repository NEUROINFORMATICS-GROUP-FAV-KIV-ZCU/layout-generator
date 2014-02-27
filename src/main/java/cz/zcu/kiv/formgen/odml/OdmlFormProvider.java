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
 * OdmlFormProvider.java, 2. 12. 2013 17:22:18 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml;

import cz.zcu.kiv.formgen.core.FormProvider;
import cz.zcu.kiv.formgen.core.TypeMapper;
import cz.zcu.kiv.formgen.model.DataField;
import cz.zcu.kiv.formgen.model.DataSet;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.model.FormSet;
import cz.zcu.kiv.formgen.odml.model.OdmlDataField;
import cz.zcu.kiv.formgen.odml.model.OdmlDataSet;
import cz.zcu.kiv.formgen.odml.model.OdmlForm;
import cz.zcu.kiv.formgen.odml.model.OdmlFormField;
import cz.zcu.kiv.formgen.odml.model.OdmlFormSet;


/**
 *
 * @author Jakub Krauz
 */
public class OdmlFormProvider implements FormProvider {
    
    
    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.core.FormProvider#typeMapper()
     */
    @Override
    public TypeMapper typeMapper() {
        return OdmlTypeMapper.instance();
    }
    

    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.core.FormProvider#newForm(java.lang.String)
     */
    @Override
    public Form newForm(String name) {
        if (name == null)
            return null;
        Form form = null;
        
        try {
            form = new OdmlForm(name);
        } catch (Exception e) {
            // exception is never thrown
            e.printStackTrace();
        }
        
        return form;
    }


    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.core.FormProvider#newFormItem(java.lang.String, java.lang.Class)
     */
    @Override
    public FormField newFormField(String name, Class<?> type) {
        if (name == null || type == null)
            return null;
        FormField formItem = null;
        
        try {
            formItem = new OdmlFormField(name, type);
        } catch (Exception e) {
            // exception is never thrown
            e.printStackTrace();
        }
        
        return formItem;
    }



    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.core.FormProvider#newFormSet(java.lang.String, java.lang.Class)
     */
    @Override
    public FormSet newFormSet(String name, Class<?> type) {
        if (name == null || type == null)
            return null;
        FormSet formSet = null;
        
        try {
            formSet = new OdmlFormSet(name, type);            
        } catch (Exception e) {
            // exception is never thrown
            e.printStackTrace();
        }
        
        return formSet;
    }


    @Override
    public DataField newDataField(String name, Object value) {
        if (name == null)
            return null;
        
        DataField dataField = null;
        try {
            dataField = new OdmlDataField(name, value);
        } catch (Exception e) {
            // exception is never thrown
            e.printStackTrace();
        }
        return dataField;
    }


    @Override
    public DataSet newDataSet(String name) {
        if (name == null)
            return null;
        
        DataSet dataSet = null;
        try {
            dataSet = new OdmlDataSet(name);
        } catch (Exception e) {
            // exception is never thrown
            e.printStackTrace();
        }
        
        return dataSet;
    }




}
