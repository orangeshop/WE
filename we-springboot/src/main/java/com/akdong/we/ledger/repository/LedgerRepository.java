package com.akdong.we.ledger.repository;

import com.akdong.we.ledger.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LedgerRepository  extends JpaRepository<Ledger, Long> {
}
