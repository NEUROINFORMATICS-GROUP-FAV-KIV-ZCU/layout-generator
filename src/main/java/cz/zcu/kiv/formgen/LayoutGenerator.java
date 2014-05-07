/***********************************************************************************************************************
 *
 * This file is part of the layout-generator project
 *
 * =================================================
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
 * LayoutGenerator.java, 15. 11. 2013 17:36:16 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen;

import java.util.Collection;
import cz.zcu.kiv.formgen.model.Form;


/**
 * Controlls the process of form layouts generation.
 * 
 * @author Jakub Krauz
 */
public interface LayoutGenerator extends Generator<Class<?>, Form> {


    /**
     * Parses a class with the given name and creates an appropriate form model.
     * 
     * @param name The fully-qualified name of a class to be parsed.
     * @return The created {@link Form}.
     * @throws ClassNotFoundException If the class with the given name cannot be found.
     * @throws FormNotFoundException If the {@link cz.zcu.kiv.formgen.annotation.Form @Form}
     *          annotation was not found in the given class.
     */
    Form loadClass(String name) throws ClassNotFoundException,
                                       FormNotFoundException;


    /**
     * Parses all classes with the given names and creates appropriate form models.
     * 
     * @param names Array of fully-qualified names of classes to be parsed.
     * @return Created {@link Form} objects.
     * @throws ClassNotFoundException If any of the given classes cannot be found.
     * @throws FormNotFoundException If the {@link cz.zcu.kiv.formgen.annotation.Form @Form}
     *          annotation was not found in any of the given classes.
     */
    Collection<Form> loadClasses(String[] names) throws ClassNotFoundException,
                                                        FormNotFoundException;


    /**
     * Parses all classes with the given names and creates appropriate form models.
     * 
     * @param names Collection of fully-qualified names of classes to be parsed.
     * @return Created {@link Form} objects.
     * @throws ClassNotFoundException If any of the given classes cannot be found.
     * @throws FormNotFoundException If the {@link cz.zcu.kiv.formgen.annotation.Form @Form}
     *          annotation was not found in any of the given classes.
     */
    Collection<Form> loadClasses(Collection<String> names) throws ClassNotFoundException,
                                                                  FormNotFoundException;


    /**
     * Parses all classes in a package of the given name.
     * 
     * @param name Name of the package.
     * @return Created {@link Form} objects.
     * @throws FormNotFoundException If no class with the {@link cz.zcu.kiv.formgen.annotation.Form @Form}
     *          annotation was found in the package.
     */
    Collection<Form> loadPackage(String name) throws FormNotFoundException;


    /**
     * Parses all classes in the given package.
     * 
     * @param pack The package to be parsed.
     * @return Created {@link Form} objects.
     * @throws FormNotFoundException If no class with the {@link cz.zcu.kiv.formgen.annotation.Form @Form}
     *          annotation was found in the package.
     */
    Collection<Form> loadPackage(Package pack) throws FormNotFoundException;


    /**
     * Gets the {@link Form} model with the given name.
     * 
     * @param name Name of the form.
     * @return Requested form model or null if not found.
     */
    Form getLoadedForm(String name);

}
