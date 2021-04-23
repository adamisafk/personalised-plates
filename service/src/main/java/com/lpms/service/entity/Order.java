package com.lpms.service.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The type Order.
 */
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
     * Gets customer.
     *
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets customer.
     *
     * @param customer the customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Gets plate.
     *
     * @return the plate
     */
    public Plate getPlate() { return plate; }

    /**
     * Sets plate.
     *
     * @param plate the plate
     */
    public void setPlate(Plate plate) { this.plate = plate; }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Gets null.
     *
     * @return the null
     */
    public Boolean getNull() { return isNull; }

    /**
     * Sets null.
     *
     * @param aNull the a null
     */
    public void setNull(Boolean aNull) { isNull = aNull; }
}
