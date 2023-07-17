package com.trInfo.repository;

import com.trInfo.entity.City;
import com.trInfo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findAllByCountry_Countryid(Long id);

    City findByCityid(Long cityid);
}
