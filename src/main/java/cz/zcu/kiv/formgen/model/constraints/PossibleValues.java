/***********************************************************************************************************************
 *
 * This file is part of the layout-generator project
 *
 * =================================================
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
 * PossibleValues.java, 3. 4. 2014 18:28:58 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model.constraints;

import java.util.Arrays;


/**
 * Enumeration of values acceptable as the only input possibilities.
 *
 * @author Jakub Krauz
 */
public class PossibleValues implements Constraint {
    
    /** Name of the constraint. */
    public static final String NAME = "possibleValues";
    
    /** Array of possible values. */
    private Object[] values;
    
    
    /**
     * Constructs a new possible values constraint.
     * @param values array of possible values
     */
    public PossibleValues(Object[] values) {
        this.values = values;
    }

    
    /**
     * {@inheritDoc}
     */
    @Override
    public String name() {
        return NAME;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Object value() {
        return (Object) values;
    }
    
    
    /**
     * Returns the array of possible values.
     * @return array of possible values
     */
    public Object[] getValues() {
        return values;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(values);
        return result;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        PossibleValues other = (PossibleValues) obj;
        if (!Arrays.equals(values, other.values)) { return false; }
        return true;
    }

}
