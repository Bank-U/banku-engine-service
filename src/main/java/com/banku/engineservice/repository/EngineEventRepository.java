package com.banku.engineservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.banku.engineservice.event.EngineEvent;

import java.util.List;

@Repository
public interface EngineEventRepository extends MongoRepository<EngineEvent, String> {
    List<EngineEvent> findByAggregateIdOrderByVersionAsc(String aggregateId);
    List<EngineEvent> findByUserIdOrderByVersionAsc(String userId);
} 