package com.autorepairportal.autorepairportal.models;

import javax.persistence.*;

@Entity
public class days {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Days")
    private Long ID;

    @Column(name = "Name")
    private String Name;

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

    public days(){}
}
