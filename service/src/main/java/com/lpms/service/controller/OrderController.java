package com.lpms.service.controller;

import com.lpms.service.entity.Customer;
import com.lpms.service.entity.Order;
import com.lpms.service.entity.Plate;
import com.lpms.service.entity.request.*;
import com.lpms.service.repository.OrderRepository;
import com.lpms.service.repository.PlateRepository;
import com.lpms.service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    @Caching(evict = {
        @CacheEvict(value = "ordersGetByToken", key = "#token"),
        @CacheEvict(value = "platesWithStyle", allEntries = true),
        @CacheEvict(value = "platesWithoutStyle", allEntries = true),
        @CacheEvict(value = "platesWithId", allEntries = true)
    })
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
        // Check if "On Sale" order exists for the plate. If it does, update the order to "Sold" and nullify
        Order saleOrder = orderRepository.findTopByPlateAndIsNullFalseAndStatusEquals(plate, 4);
        if(saleOrder != null) {
            saleOrder.setStatus(5);
            saleOrder.setNull(true);
            orderRepository.save(saleOrder);
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
    @Caching(evict = {
            @CacheEvict(value = "ordersGetByToken", key = "#token"),
            @CacheEvict(value = "platesWithStyle", allEntries = true),
            @CacheEvict(value = "platesWithoutStyle", allEntries = true),
            @CacheEvict(value = "platesWithId", allEntries = true)
    })
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
    @Caching(evict = {
            @CacheEvict(value = "ordersGetByToken", key = "#token"),
            @CacheEvict(value = "platesWithStyle", allEntries = true),
            @CacheEvict(value = "platesWithoutStyle", allEntries = true),
            @CacheEvict(value = "platesWithId", allEntries = true)
    })
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

    // POST Create New 'For Sale' order
    @PostMapping(value = "/create/sale")
    @Caching(evict = {
            @CacheEvict(value = "ordersGetByToken", key = "#token"),
            @CacheEvict(value = "platesWithStyle", allEntries = true),
            @CacheEvict(value = "platesWithoutStyle", allEntries = true),
            @CacheEvict(value = "platesWithId", allEntries = true)
    })
    public ResponseEntity<String> createSaleOrder(@RequestHeader("Authorization") String token, @RequestBody OrderCreateSaleRequest orderCreateSaleRequest) {
        // Check if customer identified by token is same as customer who owns plate
        Boolean isAuth = doesCustomerOwnOrder(token, orderRepository.findById(orderCreateSaleRequest.getPrevOrderId()));
        if(!isAuth) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Mismatch of authorized customer and customer of the order.");
        }
        // Try parsing price as double
        double price;
        try {
            price = Double.parseDouble(orderCreateSaleRequest.getPrice());
        } catch(NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to parse double");
        }
        // Nullify previous order
        Optional<Order> optionalOrder = orderRepository.findById(orderCreateSaleRequest.getPrevOrderId());
        Order prevOrder = optionalOrder.get();
        prevOrder.setNull(true);
        orderRepository.save(prevOrder);
        // Update plate data
        Plate plate = prevOrder.getPlate();
        plate.setAllocated(false);
        plate.setPrice(price);
        // Create new sale order
        Order order = new Order();
        order.setPlate(plate);
        Timestamp date = new Timestamp(new Date().getTime());
        order.setDate(date);
        order.setNull(false);
        order.setStatus(4);
        order.setCustomer(customerService.readCustomerByToken(token));
        orderRepository.save(order);

        return ResponseEntity.ok().body("Plate is now on sale!");
    }

    // POST Cancel Sale Order by Deleting Entry
    @PostMapping("/delete")
    @Caching(evict = {
            @CacheEvict(value = "ordersGetByToken", key = "#token"),
            @CacheEvict(value = "platesWithStyle", allEntries = true),
            @CacheEvict(value = "platesWithoutStyle", allEntries = true),
            @CacheEvict(value = "platesWithId", allEntries = true)
    })
    public ResponseEntity<String> cancelSaleOrder(@RequestHeader("Authorization") String token, @RequestBody OrderCancelSaleRequest orderCancelSaleRequest) {
        // Check if customer identified by token is same as customer who owns plate
        Boolean isAuth = doesCustomerOwnOrder(token, orderRepository.findById(orderCancelSaleRequest.getOrderId()));
        if(!isAuth) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Mismatch of authorized customer and customer of the order.");
        }
        // Get order
        Optional<Order> optionalOrder = orderRepository.findById(orderCancelSaleRequest.getOrderId());
        Order order = optionalOrder.get();
        // Check if order has "On Sale" status
        if(order.getStatus() != 4) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Order is not on sale, cannot cancel.");
        }
        // Delete order
        orderRepository.delete(order);
        // Get previous order, revert order nullification and re-allocated plate
        Customer customer = customerService.readCustomerByToken(token);
        Optional<Plate> optionalPlate = plateRepository.findById(order.getPlate().getId());
        Plate plate = optionalPlate.get();
        plate.setAllocated(true);
        Order prevOrder = orderRepository.findTopByCustomerAndPlateAndIsNullTrue(customer, plate);
        prevOrder.setNull(false);
        orderRepository.save(prevOrder);

        return ResponseEntity.ok().body("Sale order has been cancelled!");
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
