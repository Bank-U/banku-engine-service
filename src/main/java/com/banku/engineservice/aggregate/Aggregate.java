package com.banku.engineservice.aggregate;

import java.util.UUID;

import com.banku.engineservice.event.EngineEvent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Aggregate {
    private String id;
    protected long version;
    protected String userId;
    
    protected Aggregate() {
        this.id = UUID.randomUUID().toString();
        this.version = 0;
    }
    
    public abstract void apply(EngineEvent event);
} 