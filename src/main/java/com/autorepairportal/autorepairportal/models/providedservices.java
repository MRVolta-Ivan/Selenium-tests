package com.autorepairportal.autorepairportal.models;

import javax.persistence.*;

@Entity
public class providedservices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ProvidedServices")
    private Long ID;

    @Column(name = "Name")
    private String Name;

    @Column(name = "Cost")
    private String Cost;

    @Column(name = "Description")
    private String Description;

    @Column(name = "Photo")
    private String Photo;

    @Column(name = "ID_RepairShop")
    private Long ID_RepairShop;

    @Column(name = "ID_Services")
    private Long ID_Services;

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

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public Long getID_RepairShop() {
        return ID_RepairShop;
    }

    public void setID_RepairShop(Long ID_RepairShop) {
        this.ID_RepairShop = ID_RepairShop;
    }

    public Long getID_Services() {
        return ID_Services;
    }

    public void setID_Services(Long ID_Services) {
        this.ID_Services = ID_Services;
    }

    public providedservices(){}

    public providedservices(String Name, String Cost, String Description, Long RepairShop, Long Services){
        this.Cost = Cost;
        this.Name = Name;
        this.Description = Description;
        this.ID_RepairShop = RepairShop;
        this.ID_Services = Services;

        if (Services == 1)
            this.Photo = "shin.jpg";
        else if (Services == 2)
            this.Photo = "masl.jpg";
        else if (Services == 3)
            this.Photo = "dvig.jpg";
    }
}
