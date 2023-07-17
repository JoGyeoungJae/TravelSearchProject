package com.trInfo.repository;

import com.trInfo.entity.FestivalImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FestivalImgRepository extends JpaRepository<FestivalImg, Long> {

    //List<FestivalImg> findByFidOrderByIdAsc(Long fid);
    List<FestivalImg> findAllByFestivalinfo_FidOrderByFiidAsc(Long fid);

    FestivalImg findAllByFestivalinfo_FidAndRepImgYn(Long tid, String regImgYN);
}
