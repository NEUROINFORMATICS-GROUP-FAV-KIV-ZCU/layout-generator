package example.pojo;

import cz.zcu.kiv.formgen.annotation.FormItem;


public class Pokus {
    
    @FormItem
    private short shortNumber;
    
    @FormItem
    private Byte bajt;

    
    public short getShortNumber() {
        return shortNumber;
    }

    
    public void setShortNumber(short shortNumber) {
        this.shortNumber = shortNumber;
    }

    
    public Byte getBajt() {
        return bajt;
    }

    
    public void setBajt(Byte bajt) {
        this.bajt = bajt;
    }

}
