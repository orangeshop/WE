package com.akdong.we.ledger.repository;

import com.akdong.we.ledger.entity.LedgerGift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LedgerGiftRepository  extends JpaRepository<LedgerGift, Long> {
}
