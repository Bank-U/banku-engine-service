package com.banku.engineservice.aggregate;

import com.banku.engineservice.event.AlertCreatedEvent;
import com.banku.engineservice.event.AlertResolvedEvent;
import com.banku.engineservice.event.EngineEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

@Data
@EqualsAndHashCode(callSuper = false)
public class AlertAggregate extends Aggregate {
    
    @Id
    private String id;
    private EngineEvent.EventCategory category;
    private String title;
    private String description;
    private boolean resolved;
    private long lastUpdated;
    private AlertCreatedEvent.AlertType type;

    public AlertAggregate() {
        super();
    }
    
    @Override
    public void apply(EngineEvent event) {
        if (event instanceof AlertCreatedEvent alertCreatedEvent) {
            apply(alertCreatedEvent);
        } else if (event instanceof AlertResolvedEvent alertResolvedEvent) {
            apply(alertResolvedEvent);
        }
        this.version = event.getVersion();
    }
    
    protected void apply(AlertCreatedEvent event) {
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
    
    protected void apply(AlertResolvedEvent event) {
        if (event.getResolved() != null) {
            this.resolved = event.getResolved();
        }
        this.lastUpdated = event.getTimestamp();
    }
} 