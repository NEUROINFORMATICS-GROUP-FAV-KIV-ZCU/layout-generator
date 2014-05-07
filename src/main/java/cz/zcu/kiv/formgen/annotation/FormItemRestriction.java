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
 * FormItemRestriction.java, 25. 11. 2013 20:10:49 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Adds restrictions to form items.
 *
 * @author Jakub Krauz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FormItemRestriction {
    
    /** The minimum length of the input value. */
    int minLength() default -1;
    
    /** The maximum length of the input value. */
    int maxLength() default -1;
    
    /** The minimum numeric value (for numbers only). */
    double minValue() default Double.NaN;
    
    /** The maximum numeric value (for numbers only). */
    double maxValue() default Double.NaN;
    
    /** Default value (for text items only). */
    String defaultValue() default "";
    
    /** Enumeration of possible values (text). */
    String[] values() default "";
    
}
