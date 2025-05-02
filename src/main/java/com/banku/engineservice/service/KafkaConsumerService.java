package com.banku.engineservice.service;

import com.banku.engineservice.aggregate.UserAggregate;
import com.banku.engineservice.event.AlertCreatedEvent;
import com.banku.engineservice.event.RecommendationCreatedEvent;
import com.banku.engineservice.event.UserCreatedEvent;
import com.banku.engineservice.event.UserUpdatedEvent;
import com.banku.engineservice.repository.UserAggregateRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final EngineEventService engineEventService;
    private final ObjectMapper objectMapper;
    private final OpenAIService openAIService;
    private final UserEventService userEventService;
    private final UserAggregateRepository userAggregateRepository;

    @KafkaListener(topics = "banku.openbanking", groupId = "${spring.kafka.consumer.group-id}")
    public void processOpenBankingData(String message) {
        try {
            JsonNode data = objectMapper.readTree(message);
            String userId = data.get("userId").asText();

            // Get user's preferred language
            UserAggregate user = userAggregateRepository.findById(userId);
            String language = user != null ? user.getPreferredLanguage() : "en";
            
            log.info("############# Processing Open Banking data for user: {} with language: {}", userId, language);

            // Process account data
            if (data.has("accounts")) {
                processAccountData(userId, data.get("accounts"));
            }
            
            // Process transaction data using OpenAI
            if (data.has("transactions")) {
                processTransactionDataWithAI(userId, data.get("transactions"), language);
            }
            
        } catch (Exception e) {
            log.error("Error processing Kafka message: {}", e.getMessage());
        }
    }

    private void processAccountData(String userId, JsonNode accounts) {
        for (JsonNode account : accounts) {
            BigDecimal balance = new BigDecimal(account.get("balance").asText());
            
            // Check for low balance
            if (balance.compareTo(new BigDecimal("50")) < 0) {
                AlertCreatedEvent alertEvent = new AlertCreatedEvent();
                alertEvent.setUserId(userId);
                alertEvent.setType(AlertCreatedEvent.AlertType.WARNING);
                alertEvent.setTitle("Low Balance Alert");
                alertEvent.setDescription("Account balance is below €50");
                engineEventService.saveEvent(alertEvent);
            }
            
            // Generate savings recommendations
            if (balance.compareTo(new BigDecimal("1000")) > 0) {
                RecommendationCreatedEvent recEvent = new RecommendationCreatedEvent();
                recEvent.setUserId(userId);
                recEvent.setType(RecommendationCreatedEvent.RecommendationType.INVESTMENT);
                recEvent.setTitle("Investment Opportunity");
                recEvent.setDescription("Consider investing excess funds for better returns");
                engineEventService.saveEvent(recEvent);
            }
        }
    }

    private void processTransactionDataWithAI(String userId, JsonNode transactions, String language) {
        try {
            String transactionsJson = objectMapper.writeValueAsString(transactions);
            log.debug("Sending Transactions to process with AI: {}", transactionsJson);

            openAIService.generateAlerts(userId, transactionsJson, language).stream()
                .map(engineEventService::saveEvent)
                .forEach(alert -> log.debug("Saved alert event: {}", alert));

            openAIService.generateRecommendations(userId, transactionsJson, language).stream()
                .map(engineEventService::saveEvent)
                .forEach(rec -> log.debug("Saved recommendation event: {}", rec));
            
        } catch (Exception e) {
            log.error("Error processing transactions with AI: {}", e.getMessage());
        }
    }

        
    @KafkaListener(topics = "banku.user", groupId = "${spring.kafka.consumer.group-id}")
    public void processUserEvent(String message) {
        try {
            JsonNode data = objectMapper.readTree(message);

            if (data.has("preferredLanguage")) {
                processPreferredLanguage(data);
            }

            
        } catch (Exception e) {
            log.error("Error processing Kafka message: {}", e.getMessage());
        }
    }

    private void processPreferredLanguage(JsonNode data) {
        if (data.get("eventType").asText().equals("UserCreatedEvent")) {
            UserCreatedEvent userCreatedEvent = new UserCreatedEvent();
            userCreatedEvent.setAggregateId(Optional.ofNullable(data.get("aggregateId")).map(JsonNode::asText).orElse(null));
            userCreatedEvent.setPreferredLanguage(Optional.ofNullable(data.get("preferredLanguage")).map(JsonNode::asText).orElse(null));
            userCreatedEvent.setVersion(Optional.ofNullable(data.get("version")).map(JsonNode::asLong).orElse(0L));
            userEventService.processUserEvent(userCreatedEvent);
        } else if (data.get("eventType").asText().equals("UserUpdatedEvent")) {
            UserUpdatedEvent userUpdatedEvent = new UserUpdatedEvent();
            userUpdatedEvent.setAggregateId(Optional.ofNullable(data.get("aggregateId")).map(JsonNode::asText).orElse(null));
            userUpdatedEvent.setPreferredLanguage(Optional.ofNullable(data.get("preferredLanguage")).map(JsonNode::asText).orElse(null));
            userUpdatedEvent.setVersion(Optional.ofNullable(data.get("version")).map(JsonNode::asLong).orElse(0L));
            userEventService.processUserEvent(userUpdatedEvent);
        }
    }
} 