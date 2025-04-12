package com.banku.engineservice.aggregate;

import com.banku.engineservice.event.EngineEvent;
import com.banku.engineservice.event.RecommendationCreatedEvent;
import com.banku.engineservice.event.RecommendationImplementedEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

@Data
@EqualsAndHashCode(callSuper = false)
public class RecommendationAggregate extends Aggregate {
    
    @Id
    private String id;
    private EngineEvent.EventCategory category;
    private String title;
    private String description;
    private boolean resolved;
    private long lastUpdated;
    private RecommendationCreatedEvent.RecommendationType type;

    public RecommendationAggregate() {
        super();
    }
    
    @Override
    public void apply(EngineEvent event) {
        if (event instanceof RecommendationCreatedEvent) {
            apply((RecommendationCreatedEvent) event);
            this.version++;
        } else if (event instanceof RecommendationImplementedEvent) {
            apply((RecommendationImplementedEvent) event);
            this.version++;
        }
    }
    
    protected void apply(RecommendationCreatedEvent event) {
        if (event.getId () != null) {
            this.id = event.getAggregateId();
        }
        if (event.getUserId() != null) {
            this.userId = event.getUserId();
        }
        if (event.getCategory() != null) {
            this.category = event.getCategory();
        }
        if (event.getTitle() != null) {
            this.title = event.getTitle();
        }
        if (event.getDescription() != null) {
            this.description = event.getDescription();
        }
        if (event.getType() != null) {
            this.type = event.getType();
        }
        this.lastUpdated = event.getTimestamp();

    }
    
    protected void apply(RecommendationImplementedEvent event) {
        if (event.getResolved() != null) {
            this.resolved = event.getResolved();
        }
        this.lastUpdated = event.getTimestamp();
    }
} 