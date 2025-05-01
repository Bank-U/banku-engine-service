package com.banku.engineservice.controller;

import com.banku.engineservice.event.EngineEvent;
import com.banku.engineservice.event.RecommendationImplementedEvent;
import com.banku.engineservice.service.EngineEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/engine/recommendations")
@Tag(name = "Recommendations", description = "Recommendations APIs")
@SecurityRequirement(name = "bearerAuth")
public class RecommendationsController {
    
    private final EngineEventService eventService;

    public RecommendationsController(EngineEventService eventService) {
        this.eventService = eventService;
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<EngineEvent> resolveRecommendation(@PathVariable String id) {
        EngineEvent eventUpdated = eventService.updateEvent(id, new RecommendationImplementedEvent());
        if (eventUpdated != null) {
            return ResponseEntity.ok(eventUpdated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}