package com.lambdaschool.javacountries.controllers;

import com.lambdaschool.javacountries.models.Country;
import com.lambdaschool.javacountries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController {
    @Autowired
    CountryRepository countryRepository;

    // http://localhost:2019/names/all
    @GetMapping(value = "/names/all", produces = "application/json")
    public ResponseEntity<?> getAllCountries(){
        List<Country> myCountries = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myCountries::add);

        return new ResponseEntity<>(myCountries, HttpStatus.OK);
    }

    // http://localhost:2019/names/start/u

    // http://localhost:2019/population/total

    // http://localhost:2019/population/min

    // http://localhost:2019/population/max

}
