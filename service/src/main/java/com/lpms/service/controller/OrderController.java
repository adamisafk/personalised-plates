package com.lpms.service.controller;

import com.lpms.service.entity.Customer;
import com.lpms.service.entity.Order;
import com.lpms.service.entity.Plate;
import com.lpms.service.entity.request.OrderCreateRefundRequest;
import com.lpms.service.entity.request.OrderCreateRequest;
import com.lpms.service.entity.request.OrderTransferRequest;
import com.lpms.service.repository.OrderRepository;
import com.lpms.service.repository.PlateRepository;
import com.lpms.service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@Controller
@RequestMapping(path = "/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderRepository orderRepository;
    private final PlateRepository plateRepository;
    private final CustomerService customerService;

    /*
        --------  GET REQUESTS  --------
     */
    // Get Orders by Customer Auth Token
    @GetMapping(path = "/get")
    @Cacheable(value = "ordersGetByToken", key = "#token")
    public @ResponseBody List<Order> getOrdersByCustomerToken(@RequestHeader("Authorization") String token) {
        // Get Customer
        Customer customer = customerService.readCustomerByToken(token);
        // Get order
        return orderRepository.findByCustomer(customer);
    }


    /*
        --------  POST REQUESTS  --------
     */
    // POST New Order as Processed
    @PostMapping(path = "/create/purchase")
    @CacheEvict(value = "ordersGetByToken", key = "#token")
    public ResponseEntity createOrder(@RequestHeader("Authorization") String token, @RequestBody OrderCreateRequest orderCreateRequest) {
        // Get Customer
        Customer customer = customerService.readCustomerByToken(token);
        // Get plate object from ID
        Optional<Plate> optionalPlate = plateRepository.findById(orderCreateRequest.getPlateId());
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
        order.setNull(false);
        order.setCustomer(customer);
        order.setPlate(plate);
        Timestamp date = new Timestamp(new Date().getTime());
        order.setDate(date);
        order.setStatus(2);
        orderRepository.save(order);
        // Return ok
        return ResponseEntity.ok().build();
    }

    // POST Create New Order as Refund
    @PostMapping(path = "/create/refund")
    @CacheEvict(value = "ordersGetByToken", key = "#token")
    public ResponseEntity<String> createRefundOrder(@RequestHeader("Authorization") String token, @RequestBody OrderCreateRefundRequest orderCreateRefundRequest) {
        // Check if customer associated with token is identical to customer of request order
        Optional<Order> optionalOrder = orderRepository.findById(orderCreateRefundRequest.getOrderId());
        Order oldOrder = optionalOrder.get();
        Customer customer = customerService.readCustomerByToken(token);
        if(customer != oldOrder.getCustomer()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Mismatch of authorized customer and customer of the order.");
        }
        // Update isNull to make previous order nullified
        oldOrder.setNull(true);
        orderRepository.save(oldOrder);
        // Save new refund order
        Order order = new Order();
        order.setCustomer(customer);
        Optional<Plate> optionalPlate = plateRepository.findById(orderCreateRefundRequest.getPlateId());
        Plate plate = optionalPlate.get();
        // If plate is not allocated, return forbidden
        if(!plate.getAllocated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Plate wasn't allocated already, refund is not possible.");
        }
        plate.setAllocated(false);
        order.setPlate(plate);
        order.setStatus(3);
        Timestamp date = new Timestamp(new Date().getTime());
        order.setDate(date);
        order.setNull(false);
        orderRepository.save(order);

        return ResponseEntity.ok().body("Success!");
    }

    // POST Transfer ownership of plates
    @PostMapping(path = "/create/transfer")
    @CacheEvict(value = "ordersGetByToken", key = "#token")
    public ResponseEntity<String> transferOrder(@RequestHeader("Authorization") String token, @RequestBody OrderTransferRequest orderTransferRequest) {
        Boolean isAuth = doesCustomerOwnOrder(token, orderRepository.findById(orderTransferRequest.getOrderId()));
        if(!isAuth) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Mismatch of authorized customer and customer of the order.");
        }
        // Check if new customer exists
        if(customerService.readCustomerByEmail(orderTransferRequest.getEmail()) == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Email address not known.");
        }
        // Update isNull to make previous order nullified and update status
        Optional<Order> optionalOrder = orderRepository.findById(orderTransferRequest.getOrderId());
        Order oldOrder = optionalOrder.get();
        oldOrder.setNull(true);
        oldOrder.setStatus(6);
        orderRepository.save(oldOrder);
        // Create new order
        Order order = new Order();
        order.setPlate(oldOrder.getPlate());
        Timestamp date = new Timestamp(new Date().getTime());
        order.setDate(date);
        order.setNull(false);
        order.setStatus(6);
        // Get new customer and save order
        order.setCustomer(customerService.readCustomerByEmail(orderTransferRequest.getEmail()));
        orderRepository.save(order);

        return ResponseEntity.ok().body("Plate has been transferred!");
    }







    /*
        --------  HELPER FUNCTIONS  --------
     */
    // Check if customer associated with token is identical to customer of request order
    public Boolean doesCustomerOwnOrder(String token, Optional<Order> optionalOrder) {
        Order oldOrder = optionalOrder.get();
        Customer customer = customerService.readCustomerByToken(token);
        return customer == oldOrder.getCustomer();
    }
}
