package example.pojo;

import java.util.Date;
import cz.zcu.kiv.formgen.annotations.Form;


@Form("Osoba")
public class Person {
    
    private String name;
    
    private int age;
    
    private Date birth;
    
    public Person(String name, int age, Date birth) {
        this.name = name;
        this.age = age;
        this.birth = birth;
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
    

}
