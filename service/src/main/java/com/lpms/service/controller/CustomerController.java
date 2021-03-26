package com.lpms.service.controller;

import com.lpms.service.entity.Customer;
import com.lpms.service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    /*
        --------  GET REQUESTS  --------
     */
    // GET Customer By ID
    @GetMapping(path = "/{id}")
    public @ResponseBody Optional<Customer> getCustomer(@PathVariable Integer id) {
        return customerRepository.findById(id);
    }
    // GET All Customers
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Customer> getAllCustomers() {
       return customerRepository.findAll();
    }

    /*
        --------  POST REQUESTS  --------
     */
    // POST New Customer
    @PostMapping(path = "/create")
    public @ResponseBody String createCustomer(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) {
        System.out.println("POST REQUEST - Create New Customer");
        Customer customer = new Customer();
        System.out.println("Creating new customer entity...");
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPassword(password);
        customerRepository.save(customer);
        System.out.println("Saved Customer to DB");
        return "Created with ID: " + customer.getId();
    }
}
