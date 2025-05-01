package com.banku.engineservice.service;

import com.banku.engineservice.aggregate.AlertAggregate;
import com.banku.engineservice.aggregate.RecommendationAggregate;
import com.banku.engineservice.event.AlertCreatedEvent;
import com.banku.engineservice.event.EngineEvent;
import com.banku.engineservice.event.RecommendationCreatedEvent;
import com.banku.engineservice.repository.EngineEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EngineEventServiceTest {

    @Mock
    private EngineEventRepository eventRepository;

    @InjectMocks
    private EngineEventService engineEventService;

    @BeforeEach
    void setUp() {
        // Setup common test data
    }

    @Test
    void saveEvent_ShouldSaveNewEvent() {
        // Given
        EngineEvent event = new AlertCreatedEvent();
        event.setId("test-id");
        when(eventRepository.save(any(EngineEvent.class))).thenReturn(event);

        // When
        EngineEvent savedEvent = engineEventService.saveEvent(event);

        // Then
        assertNotNull(savedEvent);
        verify(eventRepository).save(event);
    }

    @Test
    void findAlertsById_ShouldReturnAlertAggregate() {
        // Given
        String alertId = "test-alert";
        AlertCreatedEvent event = new AlertCreatedEvent();
        event.setId(alertId);
        event.setAggregateId(alertId);
        when(eventRepository.findByAggregateIdOrderByVersionAsc(alertId)).thenReturn(List.of(event));

        // When
        AlertAggregate aggregate = (AlertAggregate) engineEventService.findAlertsById(alertId);

        // Then
        assertNotNull(aggregate);
        assertEquals(alertId, aggregate.getId());
    }

    @Test
    void findRecommendationsById_ShouldReturnRecommendationAggregate() {
        // Given
        String recommendationId = "test-recommendation";
        RecommendationCreatedEvent event = new RecommendationCreatedEvent();
        event.setId(recommendationId);
        event.setAggregateId(recommendationId);
        when(eventRepository.findByAggregateIdOrderByVersionAsc(recommendationId)).thenReturn(List.of(event));

        // When
        RecommendationAggregate aggregate = (RecommendationAggregate) engineEventService.findRecommendationsById(recommendationId);

        // Then
        assertNotNull(aggregate);
        assertEquals(recommendationId, aggregate.getId());
    }

    @Test
    void findAlertAggregatesByUserId_ShouldReturnListOfAlerts() {
        // Given
        String userId = "test-user";
        AlertCreatedEvent event = new AlertCreatedEvent();
        event.setId("test-alert");
        event.setAggregateId("test-alert");
        event.setUserId(userId);
        when(eventRepository.findByUserIdOrderByVersionAsc(userId)).thenReturn(List.of(event));

        // When
        List<AlertAggregate> aggregates = engineEventService.findAlertAggregatesByUserId(userId);

        // Then
        assertNotNull(aggregates);
        assertFalse(aggregates.isEmpty());
    }

    @Test
    void findRecommendationAggregatesByUserId_ShouldReturnListOfRecommendations() {
        // Given
        String userId = "test-user";
        RecommendationCreatedEvent event = new RecommendationCreatedEvent();
        event.setId("test-recommendation");
        event.setAggregateId("test-recommendation");
        event.setUserId(userId);
        when(eventRepository.findByUserIdOrderByVersionAsc(userId)).thenReturn(List.of(event));

        // When
        List<RecommendationAggregate> aggregates = engineEventService.findRecommendationAggregatesByUserId(userId);

        // Then
        assertNotNull(aggregates);
        assertFalse(aggregates.isEmpty());
    }
} 