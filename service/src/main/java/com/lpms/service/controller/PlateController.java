package com.lpms.service.controller;

import com.lpms.service.entity.Plate;
import com.lpms.service.repository.PlateRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public @ResponseBody Iterable<Plate> getAllPlates() { return plateRepository.findAll(); }
    // GET plate by Reg and Style
    @GetMapping(path = "/find/{searchQuery}/{style}")
    public @ResponseBody Iterable<Plate> findPlateByRegAndStyle(@PathVariable String searchQuery, @PathVariable Integer style) {
        System.out.println("Hit reg and style endpoint!");
        return plateRepository.findByRegContainingAndStyle(searchQuery, style);
    }
    // GET plate by Reg
    @GetMapping(path = "/find/{searchQuery}")
    public @ResponseBody Iterable<Plate> findPlateByReg(@PathVariable String searchQuery) {
        System.out.println("Hit reg endpoint!");
        return plateRepository.findByRegContaining(searchQuery);
    }
    // GET plate by ID
    @GetMapping(path = "/{id}")
    public @ResponseBody Optional<Plate> getPlate(@PathVariable Integer id) {
        return plateRepository.findById(id);
    }
}
