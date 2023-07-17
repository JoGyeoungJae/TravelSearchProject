package com.trInfo.repository;

import com.trInfo.entity.TravelInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface TravelInfoRepository extends JpaRepository<TravelInfo, Long>{
//    QuerydslPredicateExecutor<TravelInfo>, TravelRepositoryCustom {

//    TravelInfo findByTid(Long tid);

    Optional<TravelInfo> findById(Long tid);

    List<TravelInfo> findAllByCountry_CountryidAndCity_Cityid(Long countryid, Long cityid);

    List<TravelInfo> findAllByCountry_Countryid(Long countryid);

    TravelInfo findByTid(Long tid);

}

