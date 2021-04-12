package com.autorepairportal.autorepairportal.models;

import javax.persistence.*;

@Entity
public class workdays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_WorkDays")
    private Long ID;

    @Column(name = "ID_Days")
    private Long ID_Days;

    @Column(name = "ID_RepairShop")
    private Long ID_RepairShop;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getID_Days() {
        return ID_Days;
    }

    public void setID_Days(Long ID_Days) {
        this.ID_Days = ID_Days;
    }

    public Long getID_RepairShop() {
        return ID_RepairShop;
    }

    public void setID_RepairShop(Long ID_RepairShop) {
        this.ID_RepairShop = ID_RepairShop;
    }

    public workdays(){}

    public workdays(Long Day, Long RepairShop){
        this.ID_Days = Day;
        this.ID_RepairShop = RepairShop;
    }
}
