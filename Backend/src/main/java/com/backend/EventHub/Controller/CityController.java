package com.backend.EventHub.Controller;

import com.backend.EventHub.Entity.City;
import com.backend.EventHub.Service.CityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/city")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public List<City> getAllCity() {
        return cityService.getAllCity();
    }

    @PostMapping
    public ResponseEntity<City> createCity(@Valid @RequestBody City city) {
        City savedCity = cityService.saveCity(city);
        return new ResponseEntity<>(savedCity, HttpStatus.CREATED);
    }

}
