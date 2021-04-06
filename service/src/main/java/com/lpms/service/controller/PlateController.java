package com.lpms.service.controller;

import com.lpms.service.entity.Plate;
import com.lpms.service.repository.PlateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin(origins = "http://server:80/")
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
    public @ResponseBody Iterable<Plate> getAllPlates() {
        return plateRepository.findAll();
    }
}
