package com.banku.engineservice.service;

import com.banku.engineservice.event.UserEvent;
import com.banku.engineservice.repository.UserAggregateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventService {
    private final UserAggregateRepository userAggregateRepository;

    public void processUserEvent(UserEvent event) {
        userAggregateRepository.saveUserEvent(event);
    }
} 