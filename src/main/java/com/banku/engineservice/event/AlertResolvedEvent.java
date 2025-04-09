package com.banku.engineservice.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AlertResolvedEvent extends EngineEvent {

    public AlertResolvedEvent() {
        super();
        this.category = EventCategory.ALERT;
        this.setResolved(true);
    }
} 