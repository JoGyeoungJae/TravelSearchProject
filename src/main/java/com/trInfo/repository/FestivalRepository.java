package com.trInfo.repository;

import com.trInfo.entity.FestivalInfo;
import com.trInfo.entity.TravelInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FestivalRepository extends JpaRepository<FestivalInfo, Long> {

    FestivalInfo findByFid(Long fid);

    List<FestivalInfo> findAllByCountry_CountryidAndCity_Cityid(Long countryid, Long cityid);

    List<FestivalInfo> findAllByCountry_Countryid(Long countryid);


/*
    List<Festival_Info> findAllBy();

    List<Festival_Info> findByFtitile(String ftitle);*/


}
