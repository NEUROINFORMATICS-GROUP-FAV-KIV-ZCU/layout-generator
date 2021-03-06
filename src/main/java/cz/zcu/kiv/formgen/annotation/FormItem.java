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
 * FormItem.java, 15. 11. 2013 17:36:16 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * This annotation is used to mark fields that should be included
 * in a generated form template.
 *
 * @author Jakub Krauz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FormItem {
    
    /**
     * Label of the form item. 
     * @return the label 
     */
    String label() default "";
    
    /**
     * Whether the item must be filled, or can be left blank.
     * @return true if the item is required, false otherwise
     */
    boolean required() default false;
    
    /**
     * Sets the preview of a data record. Every form can have
     * one {@link PreviewLevel#MAJOR MAJOR} preview item and
     * one {@link PreviewLevel#MINOR MINOR} preview item.
     * @return the preview level of this item
     */
    PreviewLevel preview() default PreviewLevel.NONE;

}
