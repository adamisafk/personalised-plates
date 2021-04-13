package com.lpms.service.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lpms.service.config.AuthenticationConfigConstants;
import com.lpms.service.entity.Customer;
import com.lpms.service.entity.request.CustomerCreateRequest;
import com.lpms.service.repository.CustomerRepository;
import com.lpms.service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/customer")
@RequiredArgsConstructor
public class CustomerController {
    @Autowired
    private final CustomerRepository customerRepository;
    @Autowired
    private final CustomerService customerService;

    /*
        --------  GET REQUESTS  --------
     */
    // POST Get Customer Details (Protected by authorization)
    @GetMapping(path = "/get")
    public @ResponseBody Optional<Customer> getCustomer(@RequestHeader("Authorization") String token) {
        String customerEmail = JWT.require(Algorithm.HMAC512(AuthenticationConfigConstants.SECRET.getBytes()))
                .build()
                .verify(token.replace(AuthenticationConfigConstants.TOKEN_PREFIX, ""))
                .getSubject();
        return customerRepository.findByEmail(customerEmail);
    }


    /*
        --------  POST REQUESTS  --------
     */
    // POST New Customer
    @PostMapping(path = "/register")
    public ResponseEntity createCustomer(@RequestBody CustomerCreateRequest customerCreateRequest) {
        customerService.createCustomer(customerCreateRequest);
        return ResponseEntity.ok().build();
    }
}
