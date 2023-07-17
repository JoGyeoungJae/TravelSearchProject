package com.trInfo.repository;

import com.trInfo.entity.Country;
import com.trInfo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {

    Country findByCountryid(Long countryid);
}
