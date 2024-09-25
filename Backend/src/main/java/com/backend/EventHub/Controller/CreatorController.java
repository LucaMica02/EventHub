package com.backend.EventHub.Controller;

import com.backend.EventHub.Entity.*;
import com.backend.EventHub.Service.AppUserService;
import com.backend.EventHub.Service.CreatorService;
import com.backend.EventHub.Service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/creator")
public class CreatorController {

    private final CreatorService creatorService;
    private final AppUserService appUserService;
    private final EventService eventService;
    private final EventController eventController;

    public CreatorController(CreatorService creatorService, AppUserService appUserService, EventService eventService, EventController eventController) {
        this.creatorService = creatorService;
        this.appUserService = appUserService;
        this.eventService = eventService;
        this.eventController = eventController;
    }

    private CreatorResponse helper(Creator c) {
        AppUser appUser = appUserService.getUserById(c.getUser()).get();
        CreatorResponse creatorResponse = new CreatorResponse();
        creatorResponse.setId(c.getUser());
        creatorResponse.setName(appUser.getName());
        creatorResponse.setLastname(appUser.getLastname());
        creatorResponse.setUsername(appUser.getUsername());
        creatorResponse.setEmail(appUser.getEmail());
        if (creatorService.getFeedbackCount(c.getUser()) == null) {
            creatorResponse.setFeedbacksCount(0);
            creatorResponse.setRating(BigDecimal.ZERO);
        } else {
            BigDecimal totalStars = new BigDecimal(creatorService.getTotalStars(c.getUser()));
            BigDecimal feedbackCount = new BigDecimal(creatorService.getFeedbackCount(c.getUser()));
            creatorResponse.setFeedbacksCount(creatorService.getFeedbackCount(c.getUser()));
            creatorResponse.setRating(totalStars.divide(feedbackCount, 2, RoundingMode.HALF_UP));
        }
        creatorResponse.setEventsCount(creatorService.getEventCount(c.getUser()) == null ? 0 : creatorService.getEventCount(c.getUser()));
        return creatorResponse;
    }

    @GetMapping
    public List<CreatorResponse> getAllCreator() {
        List<CreatorResponse> creatorResponses = new ArrayList<CreatorResponse>();
        List<Creator> creators = creatorService.getAllCreator();
        for (Creator c : creators) {
            creatorResponses.add(helper(c));
        }
        return creatorResponses;
    }

    @GetMapping("/{id}")
    public CreatorResponse getCreator(@PathVariable Long id) {
        return helper(creatorService.getCreator(id));
    }

    @GetMapping("/{id}/events")
    public ResponseEntity<List<EventResponse>> getEventsByCreatorId (@PathVariable Long id) {
        if (!creatorService.existsById(id)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        List<Event> events = eventService.getEventsByCreatorId(id);
        List<EventResponse> eventResponses = new ArrayList<EventResponse>();
        for (Event e : events) {
            eventResponses.add(eventController.helper(e));
        }
        if (eventResponses.isEmpty()) {
            return new ResponseEntity<>(eventResponses, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(eventResponses, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCreator(@PathVariable Long id) {
        creatorService.deleteCreator(id);
        appUserService.deleteUser(id);
        return new ResponseEntity<>("Creator with id " + id + " deleted successfully", HttpStatus.OK);
    }

    @PatchMapping("/{id}/taxID")
    public ResponseEntity<Creator> patchCreator(@PathVariable Long id, @RequestParam String taxID) {
        Creator updatedCreator = creatorService.patchCreator(id, taxID);
        return new ResponseEntity<>(updatedCreator, HttpStatus.OK);
    }
}
