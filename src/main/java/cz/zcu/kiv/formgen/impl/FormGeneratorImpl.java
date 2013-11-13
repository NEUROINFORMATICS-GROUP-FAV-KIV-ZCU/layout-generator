package cz.zcu.kiv.formgen.impl;

import java.io.OutputStream;
import odml.core.Section;
import cz.zcu.kiv.formgen.FormGenerator;


public class FormGeneratorImpl implements FormGenerator {
    
    private ClassParser parser = new ClassParser();
    
    private Section loadedForm;

    public void loadClass(String name) throws ClassNotFoundException {
        Class<?> cls = Class.forName(name);
        loadedForm = parser.parse(cls);
    }


    public void loadPackage(String name) {
        // TODO Auto-generated method stub

    }


    public void writeLayout(OutputStream out) {
        // TODO Auto-generated method stub

    }

}
