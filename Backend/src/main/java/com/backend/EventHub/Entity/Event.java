package com.backend.EventHub.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "The event have to have a creator")
    @Min(value = 1, message = "The creator id have to be positive")
    private long creator;

    @NotBlank(message = "The title can't be empty or null")
    private String title;

    @NotBlank(message = "The description can't be empty or null")
    private String description;

    @NotNull(message = "The maxPeople can't be null")
    @Min(value = 1, message = "The maxPeople have to be at least 1")
    private Integer maxpeople;

    @NotNull(message = "The startBooking can't be null")
    private LocalDateTime startbooking;

    @NotNull(message = "The endBooking can't be null")
    private LocalDateTime endbooking;

    @NotNull(message = "The startEvent can't be null")
    private LocalDateTime startevent;

    @NotNull(message = "The endEvent can't be null")
    private LocalDateTime endevent;

    @NotNull(message = "The state can't be null")
    @Enumerated(EnumType.STRING)
    private EventState state;

    @Column(name = "price", precision = 10, scale = 2)
    @NotNull(message = "The price can't be null")
    @Min(value = 0, message = "The price can't be negative")
    private BigDecimal price;

    public Event() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxpeople() {
        return maxpeople;
    }

    public void setMaxpeople(Integer maxpeople) {
        this.maxpeople = maxpeople;
    }

    public LocalDateTime getStartbooking() {
        return startbooking;
    }

    public void setStartbooking(LocalDateTime startbooking) {
        this.startbooking = startbooking;
    }

    public LocalDateTime getEndbooking() {
        return endbooking;
    }

    public void setEndbooking(LocalDateTime endbooking) {
        this.endbooking = endbooking;
    }

    public LocalDateTime getStartevent() {
        return startevent;
    }

    public void setStartevent(LocalDateTime startevent) {
        this.startevent = startevent;
    }

    public LocalDateTime getEndevent() {
        return endevent;
    }

    public void setEndevent(LocalDateTime endevent) {
        this.endevent = endevent;
    }

    public EventState getState() {
        return state;
    }

    public void setState(EventState state) {
        this.state = state;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}