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
 * FormFieldTest.java, 3. 3. 2014 19:57:49 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.model;

import static org.junit.Assert.*;
import org.junit.Test;
import com.gargoylesoftware.base.testing.EqualsTester;
import cz.zcu.kiv.formgen.model.constraints.Constraint;
import cz.zcu.kiv.formgen.model.constraints.PossibleValues;


/**
 * Test cases for {@link FormField}.
 *
 * @author Jakub Krauz
 */
public class FormFieldTest {

    
    /**
     * Test the equals method.
     */
    @Test
    public void testEquals() {
        FormField a = new FormField("name", Type.TEXTBOX, FieldDatatype.STRING);
        FormField b = new FormField("name", Type.TEXTBOX, FieldDatatype.STRING);
        FormField c = new FormField("name", Type.TEXTBOX, FieldDatatype.NUMBER);
        FormField d = new FormField("name", Type.TEXTBOX, FieldDatatype.STRING) { /* trivial subclass */ };
        
        new EqualsTester(a, b, c, d);
    }
    
    
    /**
     * Test adding and removing possible values constraint.
     */
    @Test
    public void testSetPossibleValues() {
        FormField field = new FormField("field", Type.TEXTBOX, FieldDatatype.INTEGER);
        assertEquals(Type.TEXTBOX, field.getType());
        
        Constraint possibleValues = new PossibleValues(new Object[] {1, 2, 3});
        field.addConstraint(possibleValues);
        assertEquals(Type.COMBOBOX, field.getType());
        
        possibleValues = new PossibleValues(new Object[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
        field.addConstraint(possibleValues);
        assertEquals(Type.CHOICE, field.getType());
        
        field.removeConstraint(possibleValues);
        assertEquals(Type.TEXTBOX, field.getType());
    }
    
}
