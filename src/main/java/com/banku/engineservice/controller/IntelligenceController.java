package com.banku.engineservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banku.engineservice.controller.dto.IntelligenceResponse;
import com.banku.engineservice.security.JwtService;
import com.banku.engineservice.service.IntelligenceService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/engine/intelligence")
@Tag(name = "Intelligence", description = "Intelligence APIs")
@SecurityRequirement(name = "bearerAuth")
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