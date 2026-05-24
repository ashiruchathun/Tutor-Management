package com.tutorportal.adminportal.controller;

import com.tutorportal.adminportal.model.Billing;
import com.tutorportal.adminportal.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/billing")
@CrossOrigin(origins = "*")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @GetMapping
    public List<Billing> getAllBillings() {
        return billingService.getAllBillings();
    }

    @PostMapping
    public Billing createBill(@RequestBody Billing billing) {
        return billingService.createBill(billing);
    }

    @PutMapping("/{id}/payment-status")
    public ResponseEntity<Billing> updatePaymentStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            return ResponseEntity.ok(billingService.updatePaymentStatus(id, status));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
