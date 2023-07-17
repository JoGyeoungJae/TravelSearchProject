package com.trInfo.repository;

import com.trInfo.entity.MediaInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<MediaInfo, Long> {

     MediaInfo findByEid(Long eid);

//     public void deleteEyEid(Long eid);


}
