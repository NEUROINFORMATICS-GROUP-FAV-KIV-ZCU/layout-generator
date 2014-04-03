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
 * Value.java, 3. 4. 2014 18:19:08 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model.constraints;


/**
 *
 * @author Jakub Krauz
 */
public class NumericalValue implements Constraint {
    
    private String name;
    
    private Number value;
    
    
    private NumericalValue(String name, Number value) {
        this.name = name;
        this.value = value;
    }
    
    
    public static NumericalValue MAX(Number value) {
        return new NumericalValue("maxValue", value);
    }
    
    
    public static NumericalValue MIN(Number value) {
        return new NumericalValue("minValue", value);
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
