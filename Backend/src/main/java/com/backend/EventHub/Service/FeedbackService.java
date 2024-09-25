package com.backend.EventHub.Service;

import com.backend.EventHub.Entity.Feedback;
import com.backend.EventHub.Repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public List<Feedback> getFeedbacksByCreator(Long id) {
        return feedbackRepository.getFeedbackByCreator(id);
    }

    public Feedback getFeedback(Long event, Long user) {
        return feedbackRepository.getByEventAndAppUser(event, user);
    }

    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public boolean existsByEventAndAppUser(Long event, Long user) {
        return feedbackRepository.existsByEventAndAppUser(event, user);
    }
}
