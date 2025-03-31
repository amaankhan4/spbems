package com.main.complaintService.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.main.complaintService.Bean.ComplaintStatus;

@Repository
public interface ComplaintStatusRepository extends JpaRepository<ComplaintStatus, Long> {
    // Custom query methods can be defined here if needed
    // For example, to find complaint status by complaint ID:

    @Query("SELECT cs FROM ComplaintStatus cs WHERE cs.complaint.complaintId = :complaintId")
    ComplaintStatus findByComplaintId(Long complaintId);

}
