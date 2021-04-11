package com.lpms.service.service;

import com.lpms.service.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

// Custom implementation of UserDetailsService
@Service
@RequiredArgsConstructor
public class CustomerDetailsService implements UserDetailsService {
    private final CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerService.readCustomerByEmail(email);
        if(customer == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(customer.getEmail(), customer.getPassword(), Collections.emptyList());
    }
}
