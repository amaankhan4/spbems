package com.main.complaintService.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.main.complaintService.Bean.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    // Custom query methods can be defined here if needed
    // For example, to find complaints by customer ID:
    @Query("SELECT c FROM Complaint c WHERE c.customerId = :customerId")
    List<Complaint> findByCustomerId(Long customerId);

    // You can also add other custom query methods as per your requirements

}
