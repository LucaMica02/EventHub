package com.backend.EventHub.Service;

import com.backend.EventHub.Entity.InPersonEvent;
import com.backend.EventHub.Repository.InPersonEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InPersonEventService {

    private final InPersonEventRepository inPersonEventRepository;

    public InPersonEventService(InPersonEventRepository inPersonEventRepository) {
        this.inPersonEventRepository = inPersonEventRepository;
    }

    public List<InPersonEvent> getAllInPersonEvent() {
        return inPersonEventRepository.findAll();
    }

    public InPersonEvent getInPersonEvent(Long id) {
        Optional<InPersonEvent> inPersonEvent = inPersonEventRepository.findById(id);
        if (inPersonEvent.isEmpty()) {
            throw new RuntimeException("In person event with id: " + id + " not found");
        }
        return inPersonEvent.get();
    }
}
