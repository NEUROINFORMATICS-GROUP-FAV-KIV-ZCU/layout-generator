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
 * Utils.java, 3. 3. 2014 18:14:28 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Jakub Krauz
 */
public class Utils {
    
    static final Logger logger = LoggerFactory.getLogger(Utils.class);
    
    
    public static Class<?> genericParameter(ParameterizedType type) {
        Type[] parameters = type.getActualTypeArguments();
        if (parameters.length == 1 && parameters[0] instanceof Class) {
            return (Class<?>) parameters[0];
        } else {
            logger.warn("The parameterized type does not have exactly one type parameter.");
            return null;
        }
    }

}
