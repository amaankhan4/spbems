package com.main.adminServices.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.main.adminServices.Bean.Consumer;
import java.util.List;

@Repository 
public interface ConsumerRepository extends JpaRepository<Consumer, Long> {

    @Query("SELECT c FROM Consumer c WHERE c.customerId = :customerId")
    List<Consumer> findByCustomerId(@Param("customerId") Long customerId);


    @Query("SELECT c FROM Consumer c WHERE c.consumerNumber = :consumerNumber")
    Consumer findByConsumerNumber(@Param("consumerNumber") String consumerNumber);

    // void deleteByConsumerId(String consumerId);

    // List<Consumer> findByCustomerIdAndStatus(String customerId, String status);

}
