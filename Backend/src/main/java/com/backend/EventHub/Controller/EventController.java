package com.backend.EventHub.Controller;

import com.backend.EventHub.Entity.*;
import com.backend.EventHub.Service.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {

    private final EventService eventService;
    private final CityService cityService;
    private final NationService nationService;
    private final AppUserService appUserService;
    private final CreatorService creatorService;
    private final OnlineEventService onlineEventService;
    private final InPersonEventService inPersonEventService;

    public EventController(EventService eventService, CityService cityService, NationService nationService, AppUserService appUserService, CreatorService creatorService, OnlineEventService onlineEventService, InPersonEventService inPersonEventService) {
        this.eventService = eventService;
        this.cityService = cityService;
        this.nationService = nationService;
        this.appUserService = appUserService;
        this.creatorService = creatorService;
        this.onlineEventService = onlineEventService;
        this.inPersonEventService = inPersonEventService;
    }

    public EventResponse helper(Event event) {
        EventResponse eventResponse = new EventResponse();
        eventResponse.setId(event.getId());
        eventResponse.setCreator(appUserService.getUserById(event.getCreator()).get().getUsername());
        eventResponse.setTitle(event.getTitle());
        eventResponse.setDescription(event.getDescription());
        eventResponse.setFreeSpots(event.getMaxpeople() - eventService.getCountPeopleEvent(event.getId()));

        if (creatorService.getFeedbackCount(event.getCreator()) == null) {
            eventResponse.setRanking(BigDecimal.ZERO);
        } else {
            BigDecimal totalStars = new BigDecimal(creatorService.getTotalStars(event.getCreator()));
            BigDecimal feedbackCount = new BigDecimal(creatorService.getFeedbackCount(event.getCreator()));
            eventResponse.setRanking(totalStars.divide(feedbackCount, 2, RoundingMode.HALF_UP));
        }

        eventResponse.setStartBooking(event.getStartbooking());
        eventResponse.setEndBooking(event.getEndbooking());
        eventResponse.setStartEvent(event.getStartevent());
        eventResponse.setEndEvent(event.getEndevent());
        eventResponse.setState(event.getState());
        eventResponse.setPrice(event.getPrice());

        if (onlineEventService.existsById(event.getId())) {
            OnlineEvent onlineEvent = onlineEventService.getEvent(event.getId());
            eventResponse.setOnline(true);
            eventResponse.setUrl(onlineEvent.getUrl());
        } else {
            InPersonEvent inPersonEvent = inPersonEventService.getInPersonEvent(event.getId());
            eventResponse.setOnline(false);
            eventResponse.setStreet(inPersonEvent.getStreet());
            eventResponse.setZipcode(inPersonEvent.getZipCode());
            // City & Nation
            eventResponse.setCity(cityService.getCityById(inPersonEvent.getCity()));
            eventResponse.setNation(cityService.getNationNameByCityId(inPersonEvent.getCity()));
        }

        return eventResponse;
    }

    @GetMapping
    public List<EventResponse> getAllEvent() {
        eventService.updateEventsStates();
        List<Event> events = eventService.getAllEvent();
        List<EventResponse> eventResponses = new ArrayList<EventResponse>();
        for (Event e : events) {
            eventResponses.add(helper(e));
        }
        return eventResponses;
    }

    @GetMapping("/{id}")
    public EventResponse getEventById(@PathVariable Long id) {
        eventService.updateEventState(eventService.getEventById(id));
        return helper(eventService.getEventById(id));
    }

    /*
    * When add a event take other argument and at the same time add also the onlineevent or inpersonevent
    * */
    @PostMapping("/create")
    public ResponseEntity<Event> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        Event event = new Event();
        event.setCreator(eventRequest.getCreator());
        event.setTitle(eventRequest.getTitle());
        event.setDescription(eventRequest.getDescription());
        event.setMaxpeople(eventRequest.getMaxpeople());
        event.setStartbooking(eventRequest.getStartbooking());
        event.setEndbooking(eventRequest.getEndbooking());
        event.setStartevent(eventRequest.getStartevent());
        event.setEndevent(eventRequest.getEndevent());
        EventState state;
        if (LocalDateTime.now().isBefore(eventRequest.getEndbooking()) && LocalDateTime.now().isAfter(eventRequest.getStartbooking())) {
            state = EventState.Open;
        } else {
            state = EventState.Closed;
        }
        event.setState(state);
        event.setPrice(eventRequest.getPrice());

        // if statement for manage logic
        if (Boolean.TRUE.equals(eventRequest.getIsOnline())) {
            OnlineEvent onlineEvent = new OnlineEvent();
            onlineEvent.setUrl(eventRequest.getUrl());
            eventService.saveOnlineEvent(event, onlineEvent);
        } else {
            InPersonEvent inPersonEvent = new InPersonEvent();
            Long nationId = nationService.getNationId(eventRequest.getNation());
            Long cityId = cityService.getCityId(eventRequest.getCity(), nationId);
            inPersonEvent.setCity(cityId);
            inPersonEvent.setStreet(eventRequest.getStreet());
            inPersonEvent.setZipCode(eventRequest.getZipcode());
            eventService.saveInPersonEvent(event, inPersonEvent);
        }
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/state")
    public ResponseEntity<Event> patchEvent(@PathVariable Long id, @RequestParam EventState state) {
        // Manage Errors:
        // 1. state not EventState
        // 2. state not Valid
        Event updatedEvent = eventService.patchEvent(id, state);
        return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
    }
}
