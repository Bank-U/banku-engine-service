package com.banku.engineservice.service;

import com.banku.engineservice.aggregate.Aggregate;
import com.banku.engineservice.aggregate.AlertAggregate;
import com.banku.engineservice.aggregate.RecommendationAggregate;
import com.banku.engineservice.event.EngineEvent;
import com.banku.engineservice.repository.EngineEventRepository;
import com.banku.engineservice.security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EngineEventService {
    
    private final EngineEventRepository eventRepository;
    
    public EngineEventService(EngineEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public EngineEvent saveEvent(EngineEvent event) {
        if (event.getAggregateId() == null) {
            event.setAggregateId(event.getId());
            return eventRepository.save(event);
        }

        Aggregate existingEvent = findExistingAggregate(event);
        if (existingEvent == null) {
            event = eventRepository.save(event);
            return event;
        }

        return null;
    }

    private Aggregate findExistingAggregate(EngineEvent event) {
        if (event.getCategory() == EngineEvent.EventCategory.ALERT) {
            return findAlertsById(event.getAggregateId());
        }
        return findRecommendationsById(event.getAggregateId());
    }

    public Aggregate findAlertsById(String id) {
        List<EngineEvent> events = eventRepository.findByAggregateIdOrderByVersionAsc(id);
        if (events.isEmpty()) {
            return null;
        }

        AlertAggregate aggregate = new AlertAggregate();
        aggregate.setId(id);
        events.forEach(aggregate::apply);
        return aggregate;
    }

    public Aggregate findRecommendationsById(String id) {
        List<EngineEvent> events = eventRepository.findByAggregateIdOrderByVersionAsc(id);
        if (events.isEmpty()) {
            return null;
        }

        RecommendationAggregate aggregate = new RecommendationAggregate();
        aggregate.setId(id);
        events.forEach(aggregate::apply);
        return aggregate;
    }

    public List<EngineEvent> findEventsByUserIdOrderByVersionAsc(String userId) {
        return eventRepository.findByUserIdOrderByVersionAsc(userId);
    }
    
    public EngineEvent updateEvent(String aggregateId, EngineEvent event) {
        Aggregate aggregate = this.findAlertsById(aggregateId);
        if (aggregate != null) {
                        
            event.setAggregateId(aggregateId);
            event.setTimestamp(System.currentTimeMillis());
            event.setUserId(JwtService.extractUserId());
            event.setVersion(aggregate.getVersion()+1); 
               
            return eventRepository.save(event);
        }
        return null;
    }
    
    public List<AlertAggregate> findAlertAggregatesByUserId(String userId) {
        Map<String, List<EngineEvent>> eventsByAggregateId = findEventsByUserIdOrderByVersionAsc(userId).stream()
            .filter(event -> event.getCategory() == EngineEvent.EventCategory.ALERT)
            .collect(Collectors.groupingBy(EngineEvent::getAggregateId));
        
        List<AlertAggregate> aggregates = new ArrayList<>();
        
        for (Map.Entry<String, List<EngineEvent>> entry : eventsByAggregateId.entrySet()) {
            AlertAggregate aggregate = new AlertAggregate();
            for (EngineEvent event : entry.getValue()) {
                aggregate.apply(event);
            }
            aggregates.add(aggregate);
        }
        
        return aggregates;
    }
    
    public List<RecommendationAggregate> findRecommendationAggregatesByUserId(String userId) {
        Map<String, List<EngineEvent>> eventsByAggregateId = findEventsByUserIdOrderByVersionAsc(userId).stream()
            .filter(event -> event.getCategory() == EngineEvent.EventCategory.RECOMMENDATION)
            .collect(Collectors.groupingBy(EngineEvent::getAggregateId));
        
        List<RecommendationAggregate> aggregates = new ArrayList<>();
        
        for (Map.Entry<String, List<EngineEvent>> entry : eventsByAggregateId.entrySet()) {
            RecommendationAggregate aggregate = new RecommendationAggregate();
            for (EngineEvent event : entry.getValue()) {
                aggregate.apply(event);
            }
            aggregates.add(aggregate);
        }
        
        return aggregates;
    }
} 