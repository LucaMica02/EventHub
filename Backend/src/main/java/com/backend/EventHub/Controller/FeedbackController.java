package com.backend.EventHub.Controller;

import com.backend.EventHub.Entity.Feedback;
import com.backend.EventHub.Service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping
    public List<Feedback> getAllFeedback() {
        return feedbackService.getAllFeedback();
    }

    @GetMapping("/get_by_creator/{id}")
    public List<Feedback> getFeedbacksByCreator(@PathVariable Long id) {
        return feedbackService.getFeedbacksByCreator(id);
    }

    @GetMapping("/get/{eventId}/{userId}")
    public Feedback getFeedback(@PathVariable Long eventId, @PathVariable Long userId) {
        return feedbackService.getFeedback(eventId, userId);
    }

    @GetMapping("/exists/{eventId}/{userId}")
    public boolean existsByEventAndAppUser(@PathVariable Long eventId, @PathVariable Long userId) {
        return feedbackService.existsByEventAndAppUser(eventId, userId);
    }

    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
        Feedback savedFeedback = feedbackService.saveFeedback(feedback);
        return new ResponseEntity<>(savedFeedback, HttpStatus.CREATED);
    }
}
