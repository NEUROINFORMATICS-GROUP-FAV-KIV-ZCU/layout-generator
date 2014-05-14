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
 * Address.java, 15. 11. 2013 17:36:16 Jakub Krauz
 *
 **********************************************************************************************************************/

package example.pojo;

import cz.zcu.kiv.formgen.annotation.Form;
import cz.zcu.kiv.formgen.annotation.FormId;
import cz.zcu.kiv.formgen.annotation.FormItem;
import cz.zcu.kiv.formgen.annotation.FormItemRestriction;
import cz.zcu.kiv.formgen.annotation.PreviewLevel;


@Form
public class Address {
    
    @FormId
    private int id;
    
    @FormItem(required = true, preview = PreviewLevel.MAJOR)
    private String town;
    
    @FormItem(required = true, preview = PreviewLevel.MINOR)
    private String street;
    
    @FormItem(required = true)
    @FormItemRestriction(minValue = 1)
    private int number;
    
    
    public Address() {
        this(0);
    }
    
    public Address(int id) {
        this(id, null, null, -1);
    }
    
    
    public Address(int id, String town, String street, int number) {
        this.id = id;
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
    
    
    public String toString() {
        return street + " " + number + ", " + town;
    }

    
    
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + number;
        result = prime * result + ((street == null) ? 0 : street.hashCode());
        result = prime * result + ((town == null) ? 0 : town.hashCode());
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
        Address other = (Address) obj;
        if (id != other.id) { return false; }
        if (number != other.number) { return false; }
        if (street == null) {
            if (other.street != null) { return false; }
        } else if (!street.equals(other.street)) { return false; }
        if (town == null) {
            if (other.town != null) { return false; }
        } else if (!town.equals(other.town)) { return false; }
        return true;
    }
    

}
