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
 * Person.java, 15. 11. 2013 17:36:16 Jakub Krauz
 *
 **********************************************************************************************************************/

package example.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import cz.zcu.kiv.formgen.annotation.Form;
import cz.zcu.kiv.formgen.annotation.FormDescription;
import cz.zcu.kiv.formgen.annotation.FormId;
import cz.zcu.kiv.formgen.annotation.FormItem;
import cz.zcu.kiv.formgen.annotation.FormItemRestriction;


@Form
@FormDescription("Form used to add persons.")
public class Person {
    
    @FormId
    private int id;
    
    @FormItem(label = "jmeno", required = true)
    @FormItemRestriction(minLength = 2, maxLength = 15)
    private String name;
    
    @FormItem
    private int age;
    
    @FormItem
    private Set<Address> addresses = new HashSet<Address>();
    
    @FormItem
    private boolean clever;
 
    //@FormItem
    private Address address;
    
    @FormItem
    private Date birth;
    
    
    
    
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
    
    
    public void addAddress(Address address) {
        this.addresses.add(address);
    }
    

}
