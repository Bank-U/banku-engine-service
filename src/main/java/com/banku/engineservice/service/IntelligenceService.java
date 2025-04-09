package com.banku.engineservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banku.engineservice.aggregate.AlertAggregate;
import com.banku.engineservice.aggregate.RecommendationAggregate;
import com.banku.engineservice.controller.dto.IntelligenceResponse;

@Service
public class IntelligenceService {
    
    @Autowired
    private EngineEventService eventService;

    public IntelligenceResponse getIntelligenceForUser(String userId) {
        IntelligenceResponse response = new IntelligenceResponse();

        List<AlertAggregate> alerts = eventService.findAlertAggregatesByUserId(userId);
        List<RecommendationAggregate> recommendations = eventService.findRecommendationAggregatesByUserId(userId);

        response.setAlerts(alerts);
        response.setRecommendations(recommendations);
        
        return response;
    }
} 