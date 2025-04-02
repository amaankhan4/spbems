package com.main.billService.Dao;

import com.main.billService.Bean.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findByConsumerNumber(String consumerNumber);
    Optional<Bill> findByBillNumber(Long billNumber);
}




