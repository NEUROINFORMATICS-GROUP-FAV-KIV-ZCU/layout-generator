package cz.zcu.kiv.formgen;

import java.io.OutputStream;


public interface FormGenerator {
    
    public void loadClass(String name) throws ClassNotFoundException;
    
    public void loadPackage(String name);
    
    public void writeLayout(OutputStream out);
    
}
