package com.banku.engineservice.repository;

import com.banku.engineservice.aggregate.UserAggregate;
import com.banku.engineservice.event.UserEvent;
import com.banku.engineservice.event.UserCreatedEvent;
import com.banku.engineservice.event.UserUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserAggregateRepository implements AggregateRepository<UserAggregate, String> {
    private final UserEventStore eventStore;

    @Override
    public UserAggregate findById(String id) {
        List<UserEvent> events = eventStore.findByAggregateIdOrderByVersionAsc(id);
        if (events.isEmpty()) {
            return null;
        }

        UserAggregate aggregate = new UserAggregate();
        events.forEach(aggregate::apply);
        return aggregate;
    }

    public void saveUserEvent(UserEvent event) {
        eventStore.save(event);
    }
} 