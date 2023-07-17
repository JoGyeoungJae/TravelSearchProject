package com.trInfo.repository;

import com.trInfo.entity.Travelimg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelImgRepository extends JpaRepository<Travelimg, Long> {

    List<Travelimg> findAllByTravelInfo_Tid(Long travelInfoId);

    Travelimg findAllByTravelInfo_TidAndRepimYn(Long tid, String regimYN);


}
