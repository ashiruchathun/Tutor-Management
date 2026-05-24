package com.tutorportal.adminportal.controller;

import com.tutorportal.adminportal.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/dashboard-stats")
    public Map<String, Object> getDashboardStats() {
        return reportService.getDashboardStats();
    }
}