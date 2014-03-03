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


/**
 * Interface that enables user to control the layout-generator tool.
 *
 * @author Jakub Krauz
 */
public interface LayoutGenerator extends Generator<Class<?>> {
    
    
    /**
     * Parses the class with given name and creates appropriate model.
     * 
     * @param names - name(s) of the class(es)
     * @throws ClassNotFoundException if the class with the given name cannot be found
     * @throws FormNotFoundException if no {@link cz.zcu.kiv.formgen.annotation.Form @Form} was found on the given class
     */
    void loadClass(String... names) throws ClassNotFoundException, FormNotFoundException;
    
    
    /**
     * Parses all classes in the given package.
     * 
     * @param name - name of the package to be parsed
     * @throws FormNotFoundException if no {@link cz.zcu.kiv.formgen.annotation.Form @Form} was found on any class in the package
     */
    void loadPackage(String name) throws FormNotFoundException;
    
    
    /**
     * Parses all classes in the given package.
     * 
     * @param pack - the package to be parsed
     * @throws FormNotFoundException if no {@link cz.zcu.kiv.formgen.annotation.Form @Form} was found on any class in the package
     */
    void loadPackage(Package pack) throws FormNotFoundException;
    
}
