package com.banku.engineservice.aggregate;

import com.banku.engineservice.event.UserEvent;
import com.banku.engineservice.event.UserCreatedEvent;
import com.banku.engineservice.event.UserUpdatedEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAggregate {
    private String preferredLanguage;

    public void apply(UserEvent event) {
        if (event instanceof UserCreatedEvent) {
            apply((UserCreatedEvent) event);
        } else if (event instanceof UserUpdatedEvent) {
            apply((UserUpdatedEvent) event);
        }
    }

    private void apply(UserCreatedEvent event) {
        this.preferredLanguage = event.getPreferredLanguage();
    }

    private void apply(UserUpdatedEvent event) {
        if (event.getPreferredLanguage() != null) {
            this.preferredLanguage = event.getPreferredLanguage();
        }
    }
} 