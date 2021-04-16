package com.lpms.service.repository;

import com.lpms.service.entity.Customer;
import com.lpms.service.entity.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findByCustomer(Customer customer);
    List<Order> findByCustomerAndIsNullIsFalse(Customer customer);
}
