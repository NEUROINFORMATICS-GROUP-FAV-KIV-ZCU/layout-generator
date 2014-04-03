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
 * MinLength.java, 3. 4. 2014 18:11:46 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model.constraints;


/**
 *
 * @author Jakub Krauz
 */
public class Length implements Constraint {
    
    private String name;
    
    private int value;
    
    
    private Length(String name, int value) {
        this.name = name;
        this.value = value;
    }
    
    
    public static Length MAX(int value) {
        return new Length("maxLength", value);
    }
    
    
    public static Length MIN(int value) {
        return new Length("minLength", value);
    }
    

    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.model.constraints.Constraint#name()
     */
    @Override
    public String name() {
        return name;
    }


    /* (non-Javadoc)
     * @see cz.zcu.kiv.formgen.model.constraints.Constraint#value()
     */
    @Override
    public Object value() {
        return (Object) value;
    }

}
