package com.lpms.service.repository;

import com.lpms.service.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * The interface Customer repository.
 */
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<Customer> findByEmail(String email);
}
