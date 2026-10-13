package com.trustconnect.controller;

import com.trustconnect.dto.DashboardResponse;
import com.trustconnect.service.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(
	    origins = "http://localhost:5173",
	    allowCredentials = "true"
	)
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/trust/{trustId}")
    public DashboardResponse getDashboard(
            @PathVariable Long trustId) {

        return dashboardService.getDashboard(trustId);
    }
}