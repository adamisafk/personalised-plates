package com.lpms.service.controller;

import com.lpms.service.entity.Plate;
import com.lpms.service.entity.request.PlateChangePriceRequest;
import com.lpms.service.repository.PlateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@Controller
@RequestMapping(path = "/plate")
@RequiredArgsConstructor
public class PlateController {
    @Autowired
    private PlateRepository plateRepository;

    /*
        --------  GET REQUESTS  --------
     */
    // GET all plates
    @GetMapping(path = "/all")
    @Cacheable(value = "platesAll")
    public @ResponseBody Iterable<Plate> getAllPlates() { return plateRepository.findAll(); }
    // GET plate by Reg and Style
    @GetMapping(path = "/find/{searchQuery}/{style}")
    @Cacheable(value = "platesWithStyle", key = "{#searchQuery, #style}")
    public @ResponseBody Iterable<Plate> findPlateByRegAndStyle(@PathVariable String searchQuery, @PathVariable Integer style) {
        return plateRepository.findByRegContainingAndStyle(searchQuery, style);
    }
    // GET plate by Reg
    @Cacheable(value = "platesWithoutStyle", key = "#searchQuery")
    @GetMapping(path = "/find/{searchQuery}")
    public @ResponseBody Iterable<Plate> findPlateByReg(@PathVariable String searchQuery) {
        return plateRepository.findByRegContaining(searchQuery);
    }
    // GET plate by ID
    @Cacheable(value = "platesWithId", key = "#id")
    @GetMapping(path = "/{id}")
    public @ResponseBody Optional<Plate> getPlate(@PathVariable Integer id) {
        return plateRepository.findById(id);
    }

    /*
        --------  POST REQUESTS  --------
     */
    // POST Change Plate Price
    @PostMapping(value = "/edit/price")
    @Caching(evict = {
            @CacheEvict(value = "ordersGetByToken", key = "#token"),
            @CacheEvict(value = "platesWithStyle", allEntries = true),
            @CacheEvict(value = "platesWithoutStyle", allEntries = true),
            @CacheEvict(value = "platesWithId", allEntries = true)
    })
    public ResponseEntity<String> changePlatePrice(@RequestHeader("Authorization") String token, @RequestBody PlateChangePriceRequest plateChangePriceRequest) {
        // Retrieve plate by ID
        Optional<Plate> optionalPlate = plateRepository.findById(plateChangePriceRequest.getPlateId());
        Plate plate = optionalPlate.get();
        // Try parsing price as double
        double price;
        try {
            price = Double.parseDouble(plateChangePriceRequest.getPrice());
        } catch(NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to parse double");
        }
        // Edit plate price and save
        plate.setPrice(price);
        plateRepository.save(plate);

        return ResponseEntity.ok().body("Plate price changed");
    }
}
