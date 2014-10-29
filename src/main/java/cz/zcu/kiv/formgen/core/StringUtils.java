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
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 *********************************************************************************************************************** 
 * 
 * StringUtils.java, 29. 10. 2014 16:36:08 Jakub Krauz
 * 
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.core;

/**
 * Contains convenience methods and utilities for working with strings.
 * 
 * @author Jakub Krauz
 */
public class StringUtils {

    /**
     * Creates a user-friendly string from a camelCase notation.
     * 
     * <p>
     * The returned string starts with an uppercase letter and individual words
     * are separated by spaces.
     * For example: "camelCaseName" is converted to "Camel case name". 
     * </p>
     * 
     * @param camelCaseWord The string in camelCase.
     * @return User-friendly string.
     */
    public static String beautifyString(String camelCaseWord) {
        // first letter must be lowercase
        if (Character.isUpperCase(camelCaseWord.charAt(0)))
            camelCaseWord = Character.toLowerCase(camelCaseWord.charAt(0)) + camelCaseWord.substring(1);

        // split the name in camelCase notation to individual words
        String[] words = camelCaseWord.split("(?=\\p{Upper})");

        // build the label from individual words separated by space
        StringBuilder buf = new StringBuilder(camelCaseWord.length() + words.length - 1);
        buf.append(Character.toUpperCase(words[0].charAt(0)));  // first letter in upper case
        buf.append(words[0].substring(1));  // rest of the first word
        for (int i = 1; i < words.length; i++) {
            buf.append(' ');
            buf.append(words[i].toLowerCase());
        }

        return buf.toString();
    }
	
}
