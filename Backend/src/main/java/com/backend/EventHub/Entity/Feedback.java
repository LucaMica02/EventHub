package com.backend.EventHub.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "Feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The event id can't be null")
    @Min(value = 1, message = "The event id have to be positive")
    private Integer event;

    @NotNull(message = "The user id can't be null")
    @Min(value = 1, message = "The user id have to be positive")
    @Column(name = "appuser")
    private Integer appUser;

    @NotNull(message = "The feedback have to have a stars")
    @Min(value = 1, message = "The stars have to be at least 1")
    @Max(value = 5, message = "The stars have to be at most 5")
    private Integer stars;

    @NotBlank(message = "The description can't be empty or null")
    private String description;

    @Column(name = "datetime")
    @NotNull(message = "The date time of the Feedback can't be null")
    private LocalDateTime dateTime;

    public Feedback() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    public Integer getUser() {
        return appUser;
    }

    public void setUser(Integer appUser) {
        this.appUser = appUser;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}