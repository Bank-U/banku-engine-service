package com.banku.engineservice.repository;

import com.banku.engineservice.event.UserEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserEventStore extends MongoRepository<UserEvent, String> {
    List<UserEvent> findByAggregateIdOrderByVersionAsc(String aggregateId);
    List<UserEvent> findByAggregateIdAndVersionGreaterThanOrderByVersionAsc(String aggregateId, long version);
} 