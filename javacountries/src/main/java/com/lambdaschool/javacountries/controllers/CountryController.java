package com.lambdaschool.javacountries.controllers;

import ch.qos.logback.core.net.SyslogOutputStream;
import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.lambdaschool.javacountries.models.Country;
import com.lambdaschool.javacountries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CountryController {
    @Autowired
    CountryRepository countryRepository;

    private List<Country> findCountries(List<Country> myCountries, CheckCountry tester){
        List<Country> tempList = new ArrayList<>();

        for(Country c : myCountries){
            if(tester.test(c)){
                tempList.add(c);
            }
        }

        return tempList;
    }

    // http://localhost:2019/names/all
    @GetMapping(value = "/names/all", produces = "application/json")
    public ResponseEntity<?> getAllCountries(){
        List<Country> myCountries = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myCountries::add);
        myCountries.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));

        return new ResponseEntity<>(myCountries, HttpStatus.OK);
    }

    // http://localhost:2019/names/start/u
    @GetMapping(value = "/names/start/{letter}", produces = "application/json")
    public ResponseEntity<?> listCountriesStartingWithLetter(@PathVariable char letter){
        List<Country> myCountries = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myCountries::add);

        List<Country> rtnList = findCountries(myCountries, c -> c.getName().charAt(0) == letter);

        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    // http://localhost:2019/population/total
    @GetMapping(value = "/population/total", produces = "application/json")
    public ResponseEntity<?> getPopulationTotal(){
        List<Country> myCountries = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myCountries::add);

        double total = 0.0;
        for(Country c : myCountries){
            total += c.getPopulation();
        }

        System.out.println("Population Total " + total);

        return  new ResponseEntity<>(total, HttpStatus.OK);
    }

    // http://localhost:2019/population/min
    @GetMapping(value = "/population/min", produces = "application/json")
    public ResponseEntity<?> getMinPopulation(){
        List<Country> myCountries = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(myCountries::add);

        myCountries.sort((c1, c2) -> (int)(c1.getPopulation() - c2.getPopulation()));

        Country minPop = myCountries.get(0);

        return new ResponseEntity<>(minPop, HttpStatus.OK);
    }

    // http://localhost:2019/population/max

}
