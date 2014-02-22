/***********************************************************************************************************************
 *
 * This file is part of the layout-generator project
 *
 * ==========================================
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
 * MultipleForm.java, 22. 2. 2014 17:25:57 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation is used to mark several POJO classes that should be transformed
 * to one (multiple) form. Note the diference from {@link Form} which maps one class
 * to one form.
 *
 * @author Jakub Krauz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MultiForm {
    
    /** Name of the form. It identifies the form, i.e. it must have exactly the same
     * value for all classes that shall be transformed to one form. */
    String value();
    
    /** Label of the form. */
    String label() default "";

}
