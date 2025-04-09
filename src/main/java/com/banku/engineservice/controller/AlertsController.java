package com.banku.engineservice.controller;

import com.banku.engineservice.event.AlertResolvedEvent;
import com.banku.engineservice.event.EngineEvent;
import com.banku.engineservice.service.EngineEventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/engine/alerts")
@Slf4j
public class AlertsController {
    
    @Autowired
    private EngineEventService eventService;
    
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