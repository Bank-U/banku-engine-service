package com.banku.engineservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedEvent extends UserEvent {
    private String preferredLanguage;

    public UserCreatedEvent(String aggregateId, String preferredLanguage) {
        this.aggregateId = aggregateId;
        this.preferredLanguage = preferredLanguage;
    }
} 