package com.backend.EventHub.Service;

import com.backend.EventHub.Entity.Event;
import com.backend.EventHub.Entity.EventState;
import com.backend.EventHub.Entity.InPersonEvent;
import com.backend.EventHub.Entity.OnlineEvent;
import com.backend.EventHub.Repository.EventRepository;
import com.backend.EventHub.Repository.InPersonEventRepository;
import com.backend.EventHub.Repository.OnlineEventRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final InPersonEventRepository inPersonEventRepository;
    private final OnlineEventRepository onlineEventRepository;
    @PersistenceContext
    private EntityManager entityManager;


    public EventService(EventRepository eventRepository, InPersonEventRepository inPersonEventRepository, OnlineEventRepository onlineEventRepository) {
        this.eventRepository = eventRepository;
        this.inPersonEventRepository = inPersonEventRepository;
        this.onlineEventRepository = onlineEventRepository;
    }

    public List<Event> getAllEvent() {
        return eventRepository.findAll();
    }

    @Transactional
    public void saveInPersonEvent(Event event, InPersonEvent inPersonEvent) {
        // Defer all constraints within this transaction
        entityManager.createNativeQuery("SET CONSTRAINTS ALL DEFERRED").executeUpdate();
        eventRepository.save(event);
        inPersonEvent.setEvent(event.getId());
        inPersonEventRepository.save(inPersonEvent);
    }

    @Transactional
    public void saveOnlineEvent(Event event, OnlineEvent onlineEvent) {
        // Defer all constraints within this transaction
        entityManager.createNativeQuery("SET CONSTRAINTS ALL DEFERRED").executeUpdate();
        eventRepository.save(event);
        onlineEvent.setEvent(event.getId());
        onlineEventRepository.save(onlineEvent);
    }

    public Event patchEvent(Long id, EventState state) {
        return eventRepository.findById(id)
                .map(event -> {
                    event.setState(state);
                    return eventRepository.save(event);
                })
                .orElseThrow(() -> new RuntimeException("Event with id " + id + " not found"));
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event with id " + id + " not found"));
    }

    public List<Event> getEventsByCreatorId(Long id) {
        return eventRepository.findByCreatorId(id);
    }

    public List<Event> getEventsByUserId(Long id) {
        return eventRepository.findByUserId(id);
    }

    public Integer getCountPeopleEvent(Long id) {
        return eventRepository.getCountPeopleEvent(id);
    }

    public void updateEventState(Event e) {
        LocalDateTime now = LocalDateTime.now();
        EventState currState = e.getState();
        if (currState == EventState.Open) {
            if (now.isAfter(e.getEndevent())) {
                eventRepository.updateEventState(e.getId(), EventState.Finished.toString());
            } else if (now.isAfter(e.getEndbooking()) || (e.getMaxpeople() - eventRepository.getCountPeopleEvent(e.getId())) <= 0) {
                eventRepository.updateEventState(e.getId(), EventState.Closed.toString());
            }
        } else if (currState == EventState.Closed){
            if (now.isAfter(e.getEndevent())) {
                eventRepository.updateEventState(e.getId(), EventState.Finished.toString());
            } else if (now.isAfter(e.getStartbooking()) && now.isBefore(e.getEndbooking()) && (e.getMaxpeople() - eventRepository.getCountPeopleEvent(e.getId())) > 0) {
                eventRepository.updateEventState(e.getId(), EventState.Open.toString());
            }
        }
    }

    public void updateEventsStates() {
        List<Event> events = eventRepository.findAll();
        for (Event e : events) {
            updateEventState(e);
        }
    }
}
