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
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 *********************************************************************************************************************** 
 * 
 * package-info.java, 27. 2. 2014 12:09:38 Jakub Krauz
 * 
 **********************************************************************************************************************/

/**
 * Contains the internal model of forms and their data.
 * 
 * <p>
 * There are two types of model used in the layout-generator library:
 * <ul>
 *     <li>form templates</li>
 *     <li>form data</li>
 * </ul>
 * The first one, form templates, are represented by the {@link Form} objects. They describe forms with their
 * items and layouts. The form data model is used to represent data records related to a form. It is represented
 * by the {@link FormData} object. 
 * </p>
 * 
 * @author Jakub Krauz
 */
package cz.zcu.kiv.formgen.model;