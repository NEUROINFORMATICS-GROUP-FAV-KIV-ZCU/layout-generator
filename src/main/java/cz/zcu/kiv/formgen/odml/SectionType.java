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
 * SectionType.java, 15. 11. 2013 19:14:06 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml;


/**
 *
 * @author Jakub Krauz
 */
public enum SectionType {
    
    FORM       ("form"), 
    TEXTBOX    ("textbox"), 
    CHECKBOX   ("checkbox"),
    DATE       ("date"),
    COMBOBOX   ("combobox"), 
    IMAGE      ("image"),
    SET        ("set");
    
    
    
    private String value;
    
    private SectionType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
