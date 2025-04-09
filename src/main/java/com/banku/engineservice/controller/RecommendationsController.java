package com.banku.engineservice.controller;

import com.banku.engineservice.event.EngineEvent;
import com.banku.engineservice.event.RecommendationImplementedEvent;
import com.banku.engineservice.service.EngineEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/engine/recommendations")
public class RecommendationsController {
    
    @Autowired
    private EngineEventService eventService;
    
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