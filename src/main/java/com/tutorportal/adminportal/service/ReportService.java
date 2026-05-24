package com.tutorportal.adminportal.service;

import com.tutorportal.adminportal.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BillingRepository billingRepository;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalTutors", tutorRepository.count());
        stats.put("activeTutors", tutorRepository.countActiveTutors());
        stats.put("totalStudents", studentRepository.count());
        stats.put("activeStudents", studentRepository.countActiveStudents());
        stats.put("totalBookings", bookingRepository.count());
        stats.put("pendingBookings", bookingRepository.countPendingBookings());

        BigDecimal revenue = billingRepository.totalRevenue();
        BigDecimal outstanding = billingRepository.totalOutstanding();
        stats.put("totalRevenue", revenue != null ? revenue : BigDecimal.ZERO);
        stats.put("outstandingAmount", outstanding != null ? outstanding : BigDecimal.ZERO);

        return stats;
    }
}
