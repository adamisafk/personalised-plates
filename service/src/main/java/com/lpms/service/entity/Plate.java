package com.lpms.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "plates")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "orders"})
public class Plate implements Serializable {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String reg;
    private Boolean allocated;
    private Double price;
    private Integer style; // 1: LLNN LLL, 2: LN LLL, 3: LLL NNN

    @OneToMany
    private List<Order> orders;

    // Getters and Setters

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

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

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
}