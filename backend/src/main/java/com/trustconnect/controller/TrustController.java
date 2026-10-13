
package com.trustconnect.controller;

import com.trustconnect.entity.Trust;
import com.trustconnect.service.TrustService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trusts")
@CrossOrigin(
    origins = "http://localhost:5173",
    allowCredentials = "true"
)
public class TrustController {

    private final TrustService trustService;

    public TrustController(TrustService trustService) {
        this.trustService = trustService;
    }

    @PostMapping
    public Trust addTrust(@RequestBody Trust trust) {
        return trustService.addTrust(trust);
    }

    @GetMapping
    public List<Trust> getAllTrusts() {
        return trustService.getAllTrusts();
    }

    @GetMapping("/{id}")
    public Trust getTrustById(@PathVariable Long id) {
        return trustService.getTrustById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteTrust(@PathVariable Long id) {
        trustService.deleteTrust(id);
        return "Trust deleted successfully";
    }
}
