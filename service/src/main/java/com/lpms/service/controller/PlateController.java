package com.lpms.service.controller;

import com.lpms.service.entity.Plate;
import com.lpms.service.repository.PlateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@Controller
@RequestMapping(path = "/plate")
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
}
