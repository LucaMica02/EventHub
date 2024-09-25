package com.backend.EventHub.Service;

import com.backend.EventHub.Entity.City;
import com.backend.EventHub.Repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAllCity() {
        return cityRepository.findAll();
    }

    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    public String getCityById(Long id) {
        Optional<City> city = cityRepository.findById(id);
        if (city.isEmpty()) {
            throw new RuntimeException("City not found for user request");
        }
        return city.get().getName();
    }

    public String getNationNameByCityId(Long id) {
        return cityRepository.getNationNameByCityId(id);
    }

    public Long getCityId(String name, Long nation) {
        if (cityRepository.existsByNameAndNation(name, nation)) {
            return cityRepository.getByNameAndNation(name, nation).getId();
        }

        City city = new City();
        city.setName(name);
        city.setNation(nation);
        cityRepository.save(city);
        return city.getId();
    }
}
