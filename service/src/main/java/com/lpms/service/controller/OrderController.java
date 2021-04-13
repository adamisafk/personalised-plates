package com.lpms.service.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lpms.service.config.AuthenticationConfigConstants;
import com.lpms.service.entity.Customer;
import com.lpms.service.entity.Order;
import com.lpms.service.entity.Plate;
import com.lpms.service.repository.CustomerRepository;
import com.lpms.service.repository.OrderRepository;
import com.lpms.service.repository.PlateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final PlateRepository plateRepository;

    /*
        --------  GET REQUESTS  --------
     */
    // Get Orders by Customer Auth Token
    @GetMapping(path = "/get")
    public @ResponseBody List<Order> getOrdersByCustomerToken(@RequestHeader("Authorization") String token) {
        // Get Customer
        String customerEmail = JWT.require(Algorithm.HMAC512(AuthenticationConfigConstants.SECRET.getBytes()))
                .build()
                .verify(token.replace(AuthenticationConfigConstants.TOKEN_PREFIX, ""))
                .getSubject();
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(customerEmail);
        Customer customer = optionalCustomer.get();
        // Get order
        return orderRepository.findByCustomer(customer);
    }


    /*
        --------  POST REQUESTS  --------
     */
    // POST New Order
    @PostMapping(path = "/create")
    public ResponseEntity createOrder(@RequestHeader("Authorization") String token, @RequestParam(value = "plateId") Integer plateId) {
        // Get Customer
        String customerEmail = JWT.require(Algorithm.HMAC512(AuthenticationConfigConstants.SECRET.getBytes()))
                .build()
                .verify(token.replace(AuthenticationConfigConstants.TOKEN_PREFIX, ""))
                .getSubject();
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(customerEmail);
        Customer customer = optionalCustomer.get();
        // Get plate object from ID
        Optional<Plate> optionalPlate = plateRepository.findById(plateId);
        Plate plate = optionalPlate.get();
        // If plate is allocated, return forbidden. Otherwise set it to allocated
        if(plate.getAllocated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            plate.setAllocated(true);
            plateRepository.save(plate);
        }
        // Save new order
        Order order = new Order();
        order.setCustomer(customer);
        order.setPlate(plate);
        Timestamp date = new Timestamp(new Date().getTime());
        order.setDate(date);
        order.setStatus(2);
        orderRepository.save(order);
        // Return ok
        return ResponseEntity.ok().build();
    }
}
