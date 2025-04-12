package com.banku.engineservice.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RecommendationImplementedEvent extends EngineEvent {
    
    public RecommendationImplementedEvent() {
        super();
        this.category = EventCategory.RECOMMENDATION;
        this.setResolved(true);
    }
} 