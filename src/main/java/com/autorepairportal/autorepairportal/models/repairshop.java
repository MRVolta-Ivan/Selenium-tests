package com.autorepairportal.autorepairportal.models;

import javax.persistence.*;

@Entity
public class repairshop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RepairShop")
    private Long ID;

    @Column(name = "Name")
    private String Name;

    @Column(name = "Address")
    private String Address;

    @Column(name = "PhoneNumber")
    private String PhoneNumber;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public repairshop(){}

    public repairshop(String Name, String Address, String PhoneNumber){
        this.Name = Name;
        this.Address = Address;
        this.PhoneNumber = PhoneNumber;
    }
}
