package com.trInfo.service;

import com.trInfo.entity.City;
import com.trInfo.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public List<City> findAllByCountry_Countryid(Long id) {

        return cityRepository.findAllByCountry_Countryid(id);
    }

    public City findByCityid(Long cityid) {

        return cityRepository.findByCityid(cityid);
    }
}
