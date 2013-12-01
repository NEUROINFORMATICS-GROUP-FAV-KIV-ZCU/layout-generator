/***********************************************************************************************************************
 *
 * This file is part of the layout-generator project
 *
 * ==========================================
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
 * OdmlWriter.java, 25. 11. 2013 19:01:38 Jakub Krauz
 *
 **********************************************************************************************************************/

package cz.zcu.kiv.formgen.odml;

import java.io.OutputStream;
import odml.core.Section;
import cz.zcu.kiv.formgen.Form;
import cz.zcu.kiv.formgen.Writer;


/**
 *
 * @author Jakub Krauz
 */
public class OdmlWriter implements Writer {

    @Override
    public void write(Form form, OutputStream outputStream) {
        Section rootSection = new Section();
        rootSection.add((Section) form);
        odml.core.OdmlWriter writer = new odml.core.OdmlWriter(rootSection);
        writer.write(outputStream);
    }

}