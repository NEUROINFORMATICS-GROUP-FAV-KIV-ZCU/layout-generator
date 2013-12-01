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
 * FormGenerator.java, 15. 11. 2013 17:36:16 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen;


import java.util.List;


public interface FormGenerator {
    
    public void loadClass(String name) throws ClassNotFoundException, FormNotFoundException;
    
    public void loadClass(Class<?> cls) throws FormNotFoundException;
    
    public void loadPackage(String name) throws FormNotFoundException;
    
    public void loadPackage(Package pack) throws FormNotFoundException;
    
    public Form getForm(String name);
    
    public List<Form> getForms();
    
}