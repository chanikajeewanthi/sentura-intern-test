package com.sentura.countries.controller;

import com.sentura.countries.model.Country;
import com.sentura.countries.service.CountryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@CrossOrigin
public class CountryController {

    private final CountryService service;

    public CountryController(CountryService service) {
        this.service = service;
    }

    @GetMapping
    public List<Country> getAllCountries(){
        return service.getCountries();
    }

    @GetMapping("/search")
    public List<Country> search(@RequestParam String name){
        return service.searchCountries(name);
    }

}