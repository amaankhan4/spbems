package com.main.complaintService.Service;

import com.main.complaintService.Bean.Complaint;
import com.main.complaintService.Bean.ComplaintStatus;
import com.main.complaintService.Dao.ComplaintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import com.main.complaintService.Dao.ComplaintStatusRepository;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import com.main.complaintService.Bean.StatusType;

public class ComplaintServiceImplementation implements ComplaintService {

    @Autowired
    private final ComplaintRepository complaintRepository;
    @Autowired 
    private final ComplaintStatusRepository complaintStatusRepository;

    public ComplaintServiceImplementation(ComplaintRepository complaintRepository,ComplaintStatusRepository complaintStatusRepository) {
        this.complaintStatusRepository = complaintStatusRepository;
        this.complaintRepository = complaintRepository;
    }

    @Override
    public Complaint registerComplaint(Complaint complaint) throws Exception {
        try{
            Complaint savedComplaint = complaintRepository.save(complaint);
            ComplaintStatus complaintStatus = new ComplaintStatus();

            complaintStatus.setComplaint(savedComplaint);
            complaintStatus.setStatus(StatusType.PENDING);
            complaintStatus.setUpdatedOn(LocalDateTime.now());
            complaintStatusRepository.save(complaintStatus);
            return savedComplaint;
        }catch(Exception e){
            throw new Exception("Error registering complaint: " + e.getMessage());
        }
    }

    @Override
    public List<Complaint> getAllComplaintsForCustomer(Long customerId) throws Exception {
        List<Complaint> complaints = complaintRepository.findByCustomerId(customerId);
        if (complaints.isEmpty()) {
            throw new Exception("No complaints found for customer with ID: " + customerId);
        }
        return complaints;
    }

    @Override
    public Optional<Complaint> getComplaintDetails(Long complaintId) {
        return complaintRepository.findById(complaintId);
    }

    @Override
    public Complaint updateComplaintStatus(Long complaintId, StatusType status) throws Exception {
        Optional<Complaint> optionalComplaint = complaintRepository.findById(complaintId);
        if (optionalComplaint.isPresent()) {
            try{ 
                Complaint complaint = optionalComplaint.get();
                complaint.setStatus(status);
                ComplaintStatus complaintStatus = complaintStatusRepository.findByComplaintId(complaintId);
                complaintStatus.setStatus(status);
                complaintStatus.setUpdatedOn(LocalDateTime.now());
                complaintStatusRepository.save(complaintStatus);

                return complaintRepository.save(complaint);
            }catch(NoSuchElementException e){
                throw new NoSuchElementException("Complaint not found with ID: " + complaintId);
            }
        }
        return null;
    }

}
