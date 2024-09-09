package com.akdong.we.couple.repository;

import com.akdong.we.couple.entity.Couple;
import com.akdong.we.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoupleRepository extends JpaRepository<Couple, Long> {
    // 특정 Member가 member1 또는 member2로 포함된 Couple을 찾는 메서드
    Optional<Couple> findByMember1OrMember2(Member member1, Member member2);
}
