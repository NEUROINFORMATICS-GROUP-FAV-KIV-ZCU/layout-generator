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
 * Pokus.java, 16. 12. 2013 13:24:52 Jakub Krauz
 *
 **********************************************************************************************************************/

package example.pojo;

import cz.zcu.kiv.formgen.annotation.Form;
import cz.zcu.kiv.formgen.annotation.FormDescription;
import cz.zcu.kiv.formgen.annotation.FormItem;


@Form("Pokus")
@FormDescription("testovaci formular")
public class Pokus {
    
    @FormItem
    java.sql.Timestamp neco;
    
    //@FormItem
    String necoJinakeho;

}
