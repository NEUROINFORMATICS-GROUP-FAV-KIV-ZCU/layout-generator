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

import cz.zcu.kiv.formgen.Form;
import cz.zcu.kiv.formgen.FormItem;
import cz.zcu.kiv.formgen.TypeMapper;
import cz.zcu.kiv.formgen.core.FormProvider;


/**
 *
 * @author Jakub Krauz
 */
public class OdmlFormProvider implements FormProvider {

    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.core.FormProvider#newForm(java.lang.String)
     */
    @Override
    public Form newForm(String name) {
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
    public FormItem newFormItem(String name, Class<?> type) {
        FormItem formItem = null;
        
        try {
            formItem = new OdmlFormItem(name, type);
        } catch (Exception e) {
            // exception is never thrown
            e.printStackTrace();
        }
        
        return formItem;
    }


    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.core.FormProvider#typeMapper()
     */
    @Override
    public TypeMapper typeMapper() {
        return OdmlTypeMapper.instance();
    }

}
