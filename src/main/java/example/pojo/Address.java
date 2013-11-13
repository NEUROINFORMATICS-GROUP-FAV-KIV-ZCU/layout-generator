package example.pojo;

import cz.zcu.kiv.formgen.annotation.FormItem;


public class Address {
    
    @FormItem(label = "mesto", required = true)
    private String town;
    
    @FormItem(required = true)
    private String street;
    
    @FormItem(required = true)
    private int number;
    
    
    public Address(String town, String street, int number) {
        this.town = town;
        this.street = street;
        this.number = number;
    }

    
    public String getTown() {
        return town;
    }

    
    public void setTown(String town) {
        this.town = town;
    }

    
    public String getStreet() {
        return street;
    }

    
    public void setStreet(String street) {
        this.street = street;
    }

    
    public int getNumber() {
        return number;
    }

    
    public void setNumber(int number) {
        this.number = number;
    }
    

}
