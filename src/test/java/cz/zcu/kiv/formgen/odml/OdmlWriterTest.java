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
 * OdmlWriterTest.java, 27. 3. 2014 18:13:26 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.util.Collection;
import org.junit.Test;
import cz.zcu.kiv.formgen.model.Form;


/**
 * Test cases for {@link OdmlWriter}.
 *
 * @author Jakub Krauz
 */
public class OdmlWriterTest {
    
    /** The writer object being tested. */
    private OdmlWriter writer = new OdmlWriter();
    
    
    /**
     * Test successfull writing {@link Form} model.
     * @throws OdmlException
     */
    @Test
    public void testWriteLayout_success() throws OdmlException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        writer.writeLayout(new Form("name"), out);
        assertTrue(out.size() > 0);
    }
    
    
    /**
     * Test writing null model.
     * @throws OdmlException
     */
    @Test
    public void testWriteLayout_nullModel() throws OdmlException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        writer.writeLayout((Form) null, out);
        assertEquals(0, out.size());
        writer.writeLayout((Collection<Form>) null, out);
        assertEquals(0, out.size());
    }
    
    
    /**
     * Test writing to null stream.
     * @throws OdmlException
     */
    @Test(expected = NullPointerException.class)
    public void testWriteLayout_nullStream() throws OdmlException {
        // should throw NullPointerException
        writer.writeLayout(new Form("name"), null);
    }
    

}
