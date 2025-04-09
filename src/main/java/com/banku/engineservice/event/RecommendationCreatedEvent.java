package com.banku.engineservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor()
public class RecommendationCreatedEvent extends EngineEvent {
    private RecommendationType type;

    
    public RecommendationCreatedEvent() {
        super();
        this.category = EventCategory.RECOMMENDATION;
    }

    public enum RecommendationType {
        SAVINGS,
        INVESTMENT,
        DEBT_MANAGEMENT,
        BUDGETING
    }
} 