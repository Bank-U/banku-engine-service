package com.banku.engineservice.controller.dto;

import lombok.Data;
import java.util.List;

import com.banku.engineservice.aggregate.AlertAggregate;
import com.banku.engineservice.aggregate.RecommendationAggregate;

@Data
public class IntelligenceResponse {
    private List<AlertAggregate> alerts;
    private List<RecommendationAggregate> recommendations;
} 