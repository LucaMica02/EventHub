package com.backend.EventHub.Service;

import com.backend.EventHub.Entity.OnlineEvent;
import com.backend.EventHub.Repository.OnlineEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OnlineEventService {

    private final OnlineEventRepository onlineEventRepository;

    public OnlineEventService(OnlineEventRepository onlineEventRepository) {
        this.onlineEventRepository = onlineEventRepository;
    }

    public List<OnlineEvent> getAllOnlineEvent() {
        return onlineEventRepository.findAll();
    }

    public boolean existsById(Long id) {
        return onlineEventRepository.existsById(id.intValue());
    }

    public OnlineEvent getEvent(Long id) {
        Optional<OnlineEvent> onlineEvent = onlineEventRepository.findById(id.intValue());
        if (onlineEvent.isEmpty()) {
            throw new RuntimeException("Event with id: " + id + " not found");
        }
        return onlineEvent.get();
    }
}
