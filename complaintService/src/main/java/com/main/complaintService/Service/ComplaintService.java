package com.main.complaintService.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.main.complaintService.Bean.Complaint;
import com.main.complaintService.Bean.StatusType;
@Service
public interface ComplaintService {
    
    Complaint registerComplaint(Complaint complaint) throws Exception;

    Optional<Complaint> getComplaintDetails(Long complaintId);

    Complaint updateComplaintStatus(Long complaintId, StatusType status) throws Exception;

    List<Complaint> getAllComplaintsForCustomer(Long customerId) throws Exception;

}
