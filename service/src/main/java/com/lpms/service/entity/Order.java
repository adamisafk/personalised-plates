package com.lpms.service.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order implements Serializable {
    // Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "plate_id")
    private Plate plate;

    private Date date;
    private Integer status; // 1: Processing, 2: Processed, 3: Refunded, 4: For Sale, 5: Sold, 6: Transferred
    private Boolean isNull;

    // Getters and Setters

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Plate getPlate() { return plate; }
    public void setPlate(Plate plate) { this.plate = plate; }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getNull() { return isNull; }
    public void setNull(Boolean aNull) { isNull = aNull; }
}
