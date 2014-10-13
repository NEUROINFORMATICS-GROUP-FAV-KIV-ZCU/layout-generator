/***********************************************************************************************************************
 *
 * This file is part of the layout-generator project
 *
 * =================================================
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
import cz.zcu.kiv.formgen.annotation.Form;
import cz.zcu.kiv.formgen.annotation.FormDescription;
import cz.zcu.kiv.formgen.annotation.FormId;
import cz.zcu.kiv.formgen.annotation.FormItem;
import cz.zcu.kiv.formgen.annotation.FormItemRestriction;
import cz.zcu.kiv.formgen.annotation.PreviewLevel;


@Form
@FormDescription("Form to create persons.")
public class Person {

    @FormId
    private int id;
    
    @FormItem(label = "Full name", required = true, preview = PreviewLevel.MAJOR)
    @FormItemRestriction(minLength = 2, maxLength = 50)
    private String fullName;
    
    @FormItem
    private int age;
    
    @FormItem
    private boolean clever;
 
    @FormItem
    private Address address;
    
    @FormItem
    private Date birth;
    
    
    public Person() {
        this(0);
    }
    
    public Person(int id) {
        this(id, null, 0, null);
    }
    
    
    public Person(int id, String name, int age, Date birth) {
        this.id = id;
        this.fullName = name;
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
        return fullName;
    }

    
    public void setName(String name) {
        this.fullName = name;
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
    
    
    public boolean isClever() {
        return clever;
    }
    
    
    public void setClever(boolean clever) {
        this.clever = clever;
    }
    
    
    
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("name: " + fullName);
        builder.append("\nage: " + age);
        builder.append("\nclever: " + clever);
        builder.append("\nbirth: " + birth);
        if (address != null)
            builder.append("\naddress: " + address.toString());
        return builder.toString();
    }

    
    
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + age;
        result = prime * result + ((birth == null) ? 0 : birth.hashCode());
        result = prime * result + (clever ? 1231 : 1237);
        result = prime * result + id;
        result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        Person other = (Person) obj;
        if (address == null) {
            if (other.address != null) { return false; }
        } else if (!address.equals(other.address)) { return false; }
        if (age != other.age) { return false; }
        if (birth == null) {
            if (other.birth != null) { return false; }
        } else if (!birth.equals(other.birth)) { return false; }
        if (clever != other.clever) { return false; }
        if (id != other.id) { return false; }
        if (fullName == null) {
            if (other.fullName != null) { return false; }
        } else if (!fullName.equals(other.fullName)) { return false; }
        return true;
    }

}
