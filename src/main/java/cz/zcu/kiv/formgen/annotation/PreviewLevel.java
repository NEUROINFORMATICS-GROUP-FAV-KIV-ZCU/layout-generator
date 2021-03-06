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
 * PreviewLevel.java, 18. 4. 2014 18:43:11 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.annotation;


/**
 * Enumeration of possible preview levels.
 * 
 * <p>
 * Preview level of a form item is used to determine which values will
 * be shown in a list of data records. Every data entity should have
 * one MAJOR level preview item and one MINOR level preview item. The others
 * should have the NONE preview level.
 * </p>
 *
 * @author Jakub Krauz
 */
public enum PreviewLevel {
    
    MAJOR, MINOR, NONE

}
