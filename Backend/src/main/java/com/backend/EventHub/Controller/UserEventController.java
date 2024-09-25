package com.backend.EventHub.Controller;

import com.backend.EventHub.Entity.UserEvent;
import com.backend.EventHub.Service.UserEventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userEvent")
public class UserEventController {

    private final UserEventService userEventService;

    public UserEventController(UserEventService userEventService) {
        this.userEventService = userEventService;
    }

    @GetMapping
    public List<UserEvent> getAllUserEvent() {
        return userEventService.getAllUserEvent();
    }

    @GetMapping("/{eventId}/{userId}")
    public boolean userEventExist(@Valid @PathVariable Long eventId, @PathVariable Long userId) {
        return userEventService.userEventExist(eventId, userId);
    }

    @PostMapping("/{eventId}/{userId}")
    public ResponseEntity<UserEvent> createUserEvent(@Valid @PathVariable Long eventId, @PathVariable Long userId) {
        UserEvent userEvent = new UserEvent();
        userEvent.setEvent(eventId);
        userEvent.setUser(userId);
        userEventService.saveUserEvent(userEvent);
        return new ResponseEntity<>(userEvent, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserEvent(@PathVariable Long id) {
        userEventService.deleteUserEvent(id);
        return new ResponseEntity<>("UserEvent with id " + id + " deleted successfully", HttpStatus.OK);
    }
}
