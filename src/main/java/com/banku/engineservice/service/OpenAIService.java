package com.banku.engineservice.service;

import com.banku.engineservice.event.AlertCreatedEvent;
import com.banku.engineservice.event.RecommendationCreatedEvent;
import com.banku.engineservice.event.EngineEvent.EventCategory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.ChatCompletionMessage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OpenAIService {

    private final ObjectMapper objectMapper;
    private OpenAIClient client;
    
    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.model}")
    private String modelName;
    
    @Value("${openai.prompts.alert}")
    private String alertPrompt;
    
    @Value("${openai.prompts.recommendation}")
    private String recommendationPrompt;
    
    public OpenAIService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    @PostConstruct
    public void init() {
        client = OpenAIOkHttpClient.builder().apiKey(apiKey).build();
    }

    public List<AlertCreatedEvent> generateAlerts(String userId, String financialData) {
        try {
            String response = callChatGPT(alertPrompt, financialData);
            log.debug("Received Alerts response from OpenAI: {}", response);

            return parseAlertResponse(userId, response);
        } catch (Exception e) {
            log.error("Error generating alerts with OpenAI: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<RecommendationCreatedEvent> generateRecommendations(String userId, String financialData) {
        try {
            String response = callChatGPT(recommendationPrompt, financialData);
            log.debug("Received Recommendations response from OpenAI: {}", response);

            return parseRecommendationResponse(userId, response);
        } catch (Exception e) {
            log.error("Error generating recommendations with OpenAI: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private String callChatGPT(String developerMessage, String transactionData) {
        ChatCompletionCreateParams createParamsBuilder = ChatCompletionCreateParams.builder()
            .model(ChatModel.GPT_4O_MINI)
            .maxCompletionTokens(2048)
            .addDeveloperMessage(developerMessage)
            .addUserMessage(transactionData)
            .build();

        return client.chat().completions().create(createParamsBuilder).choices().stream()
                .map(ChatCompletion.Choice::message)
                .map(ChatCompletionMessage::content)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.joining());
    }

    private List<AlertCreatedEvent> parseAlertResponse(String userId,String response) throws JsonProcessingException {
        List<Map<String, String>> alerts = objectMapper.readValue(response, new TypeReference<List<Map<String, String>>>() {});
        List<AlertCreatedEvent> events = new ArrayList<>();
        
        for (Map<String, String> alert : alerts) {
            AlertCreatedEvent event = new AlertCreatedEvent();
            event.setUserId(userId);
            event.setType(AlertCreatedEvent.AlertType.valueOf(alert.get("type")));
            event.setTitle(alert.get("title"));
            event.setDescription(alert.get("description"));
            log.debug("Parsed Alert: {}", event);
            events.add(event);
        }
        
        return events;
    }

    private List<RecommendationCreatedEvent> parseRecommendationResponse(String userId, String response) throws JsonProcessingException {
        List<Map<String, String>> recommendations = objectMapper.readValue(response, new TypeReference<List<Map<String, String>>>() {});
        List<RecommendationCreatedEvent> events = new ArrayList<>();
        
        for (Map<String, String> rec : recommendations) {
            RecommendationCreatedEvent event = new RecommendationCreatedEvent();
            event.setUserId(userId);
            event.setType(RecommendationCreatedEvent.RecommendationType.valueOf(rec.get("type")));
            event.setTitle(rec.get("title"));
            event.setDescription(rec.get("description"));
            log.debug("Parsed Recommendation: {}", event);
            events.add(event);
        }
        
        return events;
    }
} 