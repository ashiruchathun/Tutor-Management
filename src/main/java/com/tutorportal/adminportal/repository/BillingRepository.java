package com.tutorportal.adminportal.repository;

import com.tutorportal.adminportal.model.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
    List<Billing> findByPaymentStatus(String paymentStatus);

    @Query("SELECT COALESCE(SUM(b.amount), 0) FROM Billing b WHERE b.paymentStatus = 'PAID'")
    BigDecimal totalRevenue();

    @Query("SELECT COALESCE(SUM(b.amount), 0) FROM Billing b WHERE b.paymentStatus = 'UNPAID'")
    BigDecimal totalOutstanding();
}
