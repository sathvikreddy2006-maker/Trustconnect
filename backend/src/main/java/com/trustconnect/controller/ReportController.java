package com.trustconnect.controller;

import com.trustconnect.dto.ReportResponse;
import com.trustconnect.service.ReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(
	    origins = "http://localhost:5173",
	    allowCredentials = "true"
	)
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/trust/{trustId}")
    public ReportResponse getReport(
            @PathVariable Long trustId,
            @RequestParam String period) {

        return reportService.getReport(trustId, period);
    }
}