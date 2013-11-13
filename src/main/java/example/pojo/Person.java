package example.pojo;

import java.util.Date;
import cz.zcu.kiv.formgen.annotation.Form;
import cz.zcu.kiv.formgen.annotation.FormId;
import cz.zcu.kiv.formgen.annotation.FormItem;


@Form("Osoba")
public class Person {
    
    @FormId
    private int id;
    
    @FormItem(label = "jmeno", required = true)
    private String name;
    
    @FormItem
    private int age;
    
    private Date birth;
    
    @FormItem
    private Address address;
    
    
    public Person(int id, String name, int age, Date birth) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.birth = birth;
    }

    
    
    public int getId() {
        return id;
    }

    
    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public int getAge() {
        return age;
    }

    
    public void setAge(int age) {
        this.age = age;
    }

    
    public Date getBirth() {
        return birth;
    }

    
    public void setBirth(Date birth) {
        this.birth = birth;
    }
    
    public Address getAddress() {
        return address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    

}
