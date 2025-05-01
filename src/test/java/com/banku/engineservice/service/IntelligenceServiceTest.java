package com.banku.engineservice.service;

import com.banku.engineservice.aggregate.AlertAggregate;
import com.banku.engineservice.aggregate.RecommendationAggregate;
import com.banku.engineservice.controller.dto.IntelligenceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IntelligenceServiceTest {

    @Mock
    private EngineEventService engineEventService;

    @InjectMocks
    private IntelligenceService intelligenceService;

    @BeforeEach
    void setUp() {
        // Setup common test data
    }

    @Test
    void getIntelligenceForUser_ShouldReturnResponseWithAlertsAndRecommendations() {
        // Given
        String userId = "test-user";
        List<AlertAggregate> mockAlerts = List.of(new AlertAggregate());
        List<RecommendationAggregate> mockRecommendations = List.of(new RecommendationAggregate());
        
        when(engineEventService.findAlertAggregatesByUserId(userId)).thenReturn(mockAlerts);
        when(engineEventService.findRecommendationAggregatesByUserId(userId)).thenReturn(mockRecommendations);

        // When
        IntelligenceResponse response = intelligenceService.getIntelligenceForUser(userId);

        // Then
        assertNotNull(response);
        assertEquals(mockAlerts, response.getAlerts());
        assertEquals(mockRecommendations, response.getRecommendations());
        
        verify(engineEventService).findAlertAggregatesByUserId(userId);
        verify(engineEventService).findRecommendationAggregatesByUserId(userId);
    }
} 