package com.lpms.service.controller;

import com.lpms.service.entity.Greeting;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Greeting controller.
 * This controller is only for testing purposes to see if the endpoint can be accessed with or without authentication.
 */
@RestController
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
     * Greeting greeting.
     *
     * @param name the name
     * @return the greeting
     */
    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World!") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    /**
     * Auth greeting greeting.
     *
     * @param name the name
     * @return the greeting
     */
    @GetMapping("/authgreeting")
    public Greeting authGreeting(@RequestParam(value = "name", defaultValue = "World!") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
