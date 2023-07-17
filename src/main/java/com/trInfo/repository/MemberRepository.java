package com.trInfo.repository;

import com.trInfo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 중복된 회원 검색
    Member findByEmail(String email);

    Member findByMid(Long mid);


}
