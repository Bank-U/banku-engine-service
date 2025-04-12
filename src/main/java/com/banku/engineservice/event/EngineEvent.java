package com.banku.engineservice.event;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.time.Instant;
import java.util.UUID;

@Data
@Document(collection = "engine_events")
public class EngineEvent {
    @Id
    private String id;
    
    @Indexed
    private String userId;
    
    @Indexed
    private String aggregateId;
    
    @Indexed
    private String eventType;
    
    @Indexed
    protected EventCategory category;
    
    private long timestamp;

    private String title;

    private String description;

    private Boolean resolved;

    private long version;

    public enum EventType {
        CREATED,
        UPDATED
    }
    
    public enum EventCategory {
        ALERT,
        RECOMMENDATION
    }
    
    public EngineEvent() {
        this.id = UUID.randomUUID().toString();
        this.timestamp = Instant.now().toEpochMilli();
        this.eventType = this.getClass().getSimpleName();
        this.resolved = false;
        this.version = 0;
    }

    public EngineEvent(String title, String description) {
        this();
        this.title = title;
        this.description = description;
    }
} 