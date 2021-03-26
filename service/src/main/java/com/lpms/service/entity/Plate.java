package com.lpms.service.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Plate {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //private Customer customerId;

    private String reg;
    private Boolean allocated;
    private Double price;
    private Integer style; // 1: LLNN LLL, 2: LN LLL, 3: LLL NNN

    // Getters and Setters

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

//    public Customer getCustomerId() { return customerId; }
//    public void setCustomerId(Customer customerId) { this.customerId = customerId; }

    public String getReg() {
        return reg;
    }
    public void setReg(String reg) {
        this.reg = reg;
    }

    public Boolean getAllocated() { return allocated; }
    public void setAllocated(Boolean available) { this.allocated = available; }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStyle() {
        return style;
    }
    public void setStyle(Integer style) {
        this.style = style;
    }
}