package com.trInfo.repository;

import com.trInfo.entity.MediaImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MediaImgRepository extends JpaRepository<MediaImg, Long> {

    List<MediaImg> findAllByMediainfo_EidOrderByEiidAsc(Long eid);

//    MediaImg findAllByMediaInfo_EidAndRepimYn(Long eid, String regimYN);

    @Query(value = "select mi.img_url from mediaimg mi where mi.eid = :eid and mi.rep_img_yn = 'Y'", nativeQuery = true)
    String findRepUrl(Long eid);
}
