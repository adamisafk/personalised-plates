package com.lpms.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * The type Plate.
 */
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

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets reg.
     *
     * @return the reg
     */
    public String getReg() {
        return reg;
    }

    /**
     * Sets reg.
     *
     * @param reg the reg
     */
    public void setReg(String reg) {
        this.reg = reg;
    }

    /**
     * Gets allocated.
     *
     * @return the allocated
     */
    public Boolean getAllocated() { return allocated; }

    /**
     * Sets allocated.
     *
     * @param available the available
     */
    public void setAllocated(Boolean available) { this.allocated = available; }

    /**
     * Gets price.
     *
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Gets style.
     *
     * @return the style
     */
    public Integer getStyle() {
        return style;
    }

    /**
     * Sets style.
     *
     * @param style the style
     */
    public void setStyle(Integer style) {
        this.style = style;
    }

    /**
     * Gets orders.
     *
     * @return the orders
     */
    public List<Order> getOrders() { return orders; }

    /**
     * Sets orders.
     *
     * @param orders the orders
     */
    public void setOrders(List<Order> orders) { this.orders = orders; }
}