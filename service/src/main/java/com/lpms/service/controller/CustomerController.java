package com.lpms.service.controller;

import com.lpms.service.entity.request.CustomerCreateRequest;
import com.lpms.service.repository.CustomerRepository;
import com.lpms.service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
