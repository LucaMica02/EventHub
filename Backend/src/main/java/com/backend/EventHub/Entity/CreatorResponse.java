package com.backend.EventHub.Entity;

import java.math.BigDecimal;

public class CreatorResponse {
    private Long id;
    private String name;
    private String lastname;
    private String username;
    private String email;
    private int feedbacksCount;
    private BigDecimal rating;
    private int eventsCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFeedbacksCount() {
        return feedbacksCount;
    }

    public void setFeedbacksCount(int feedbacksCount) {
        this.feedbacksCount = feedbacksCount;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public int getEventsCount() {
        return eventsCount;
    }

    public void setEventsCount(int eventsCount) {
        this.eventsCount = eventsCount;
    }
}
