package com.banku.engineservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banku.engineservice.controller.dto.IntelligenceResponse;
import com.banku.engineservice.security.JwtService;
import com.banku.engineservice.service.IntelligenceService;

@RestController
@RequestMapping("/api/v1/engine/intelligence")
public class IntelligenceController {
    
    private final IntelligenceService intelligenceService;

    public IntelligenceController(IntelligenceService intelligenceService) {
        this.intelligenceService = intelligenceService;
    }

    @GetMapping
    public ResponseEntity<IntelligenceResponse> getIntelligence() {
        return ResponseEntity.ok(intelligenceService.getIntelligenceForUser(JwtService.extractUserId()));
    }
} 