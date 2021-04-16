package com.lpms.service.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lpms.service.config.AuthenticationConfigConstants;
import com.lpms.service.entity.Customer;
import com.lpms.service.entity.request.CustomerCreateRequest;
import com.lpms.service.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Customer readCustomerByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
    }

    public void createCustomer(CustomerCreateRequest customerCreateRequest) {
        Customer customer = new Customer();
        Optional<Customer> byEmail = customerRepository.findByEmail(customerCreateRequest.getEmail());
        if(byEmail.isPresent()) {
            throw new RuntimeException("User is already registered. Please try again.");
        }
        customer.setEmail(customerCreateRequest.getEmail());
        customer.setPassword(passwordEncoder.encode(customerCreateRequest.getPassword()));
        customer.setFirstName(customerCreateRequest.getFirstName());
        customer.setLastName(customerCreateRequest.getLastName());
        customerRepository.save(customer);
    }

    public Customer readCustomerByToken(String token) {
        String customerEmail = JWT.require(Algorithm.HMAC512(AuthenticationConfigConstants.SECRET.getBytes()))
                .build()
                .verify(token.replace(AuthenticationConfigConstants.TOKEN_PREFIX, ""))
                .getSubject();
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(customerEmail);
        return optionalCustomer.get();
    }
}
