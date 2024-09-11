package com.akdong.we.member.repository;

import com.akdong.we.member.entity.MemberAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAccountRepository extends JpaRepository<MemberAccount, Long> {
}
