package com.lpms.service.repository;

import com.lpms.service.entity.Customer;
import com.lpms.service.entity.Order;
import com.lpms.service.entity.Plate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * The interface Order repository.
 */
public interface OrderRepository extends CrudRepository<Order, Integer> {
    /**
     * Find by customer list.
     *
     * @param customer the customer
     * @return the list
     */
    List<Order> findByCustomer(Customer customer);

    /**
     * Find by customer and is null is false list.
     *
     * @param customer the customer
     * @return the list
     */
    List<Order> findByCustomerAndIsNullIsFalse(Customer customer);

    /**
     * Find top by customer and plate and is null true order.
     *
     * @param customer the customer
     * @param plate    the plate
     * @return the order
     */
    Order findTopByCustomerAndPlateAndIsNullTrue(Customer customer, Plate plate);

    /**
     * Find top by plate and is null false and status equals order.
     *
     * @param plate  the plate
     * @param status the status
     * @return the order
     */
    Order findTopByPlateAndIsNullFalseAndStatusEquals(Plate plate, Integer status);
}
