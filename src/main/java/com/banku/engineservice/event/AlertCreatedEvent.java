package com.banku.engineservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor()
public class AlertCreatedEvent extends EngineEvent {
    private AlertType type;
    
    public AlertCreatedEvent() {
        super();
        this.category = EventCategory.ALERT;
    }

    public enum AlertType {
        CRITICAL,
        WARNING,
        INFO
    }
} 