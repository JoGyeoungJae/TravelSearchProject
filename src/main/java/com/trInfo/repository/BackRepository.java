package com.trInfo.repository;

import com.trInfo.entity.Back;
import com.trInfo.entity.FestivalImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BackRepository extends JpaRepository<Back, Long> {

    List<Back> findAllByMember_Mid(Long mid);
}
