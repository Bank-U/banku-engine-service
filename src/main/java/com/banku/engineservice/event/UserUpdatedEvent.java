package com.banku.engineservice.event;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdatedEvent extends UserEvent {
    private String preferredLanguage;

    public UserUpdatedEvent(String aggregateId, String preferredLanguage) {
        this.aggregateId = aggregateId;
        this.preferredLanguage = preferredLanguage;
    }
} 