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
 * FormProvider.java, 2. 12. 2013 17:18:54 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen;

import cz.zcu.kiv.formgen.core.TypeMapper;
import cz.zcu.kiv.formgen.model.DataField;
import cz.zcu.kiv.formgen.model.DataSet;
import cz.zcu.kiv.formgen.model.Form;
import cz.zcu.kiv.formgen.model.FormField;
import cz.zcu.kiv.formgen.model.FormSet;


/**
 *
 * @author Jakub Krauz
 */
public interface ModelProvider {
    
    Form newForm(String name);
    
    FormField newFormField(String name, Class<?> type);
    
    DataField newDataField(String name, Object value);
    
    FormSet newFormSet(String name, Class<?> type);
    
    DataSet newDataSet(String name);
    
    TypeMapper typeMapper();

}
