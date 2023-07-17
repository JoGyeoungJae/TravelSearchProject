package com.trInfo.service;

import com.trInfo.entity.Country;
import com.trInfo.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CountryService {


    private final CountryRepository countryRepository;
    public List<Country> getcountrylist() {

        return countryRepository.findAll();
    }
    public Country findByCountryid(Long countryid) {

        return countryRepository.findByCountryid(countryid);
    }
}
