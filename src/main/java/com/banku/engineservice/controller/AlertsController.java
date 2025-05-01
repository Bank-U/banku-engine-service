package com.banku.engineservice.controller;

import com.banku.engineservice.event.AlertResolvedEvent;
import com.banku.engineservice.event.EngineEvent;
import com.banku.engineservice.service.EngineEventService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/engine/alerts")
@Tag(name = "Alerts", description = "Alerts APIs")
@SecurityRequirement(name = "bearerAuth")
public class AlertsController {
    
    private final EngineEventService eventService;

    public AlertsController(EngineEventService eventService) {
        this.eventService = eventService;
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<EngineEvent> resolveAlert(@PathVariable String id) {
        EngineEvent engineEvent = eventService.updateEvent(id, new AlertResolvedEvent());
        if (engineEvent != null) {
            return ResponseEntity.ok(engineEvent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
} 