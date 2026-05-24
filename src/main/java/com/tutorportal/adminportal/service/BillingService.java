package com.tutorportal.adminportal.service;

import com.tutorportal.adminportal.model.Billing;
import com.tutorportal.adminportal.repository.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepository;

    public Billing createBill(Billing billing) {
        return billingRepository.save(billing);
    }

    public List<Billing> getAllBillings() {
        return billingRepository.findAll();
    }

    public Billing updatePaymentStatus(Long id, String status) {
        return billingRepository.findById(id).map(billing -> {
            billing.setPaymentStatus(status);
            return billingRepository.save(billing);
        }).orElseThrow(() -> new RuntimeException("Billing not found with id: " + id));
    }

    public BigDecimal getTotalRevenue() {
        return billingRepository.totalRevenue();
    }

    public BigDecimal getTotalOutstanding() {
        return billingRepository.totalOutstanding();
    }
}
