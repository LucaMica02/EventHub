package com.backend.EventHub.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventResponse {

    private Long id;
    private String creator;
    private String title;
    private String description;
    private Integer freeSpots;
    private BigDecimal ranking;
    private LocalDateTime startBooking;
    private LocalDateTime endBooking;
    private LocalDateTime startEvent;
    private LocalDateTime endEvent;
    private EventState state;
    private BigDecimal price;
    private Boolean isOnline;
    private String url;
    private String street; //
    private String zipcode; //
    private String city; //
    private String nation; //

    public EventResponse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
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

    public Integer getFreeSpots() {
        return freeSpots;
    }

    public void setFreeSpots(Integer freeSpots) {
        this.freeSpots = freeSpots;
    }

    public BigDecimal getRanking() {
        return ranking;
    }

    public void setRanking(BigDecimal ranking) {
        this.ranking = ranking;
    }

    public LocalDateTime getStartBooking() {
        return startBooking;
    }

    public void setStartBooking(LocalDateTime startBooking) {
        this.startBooking = startBooking;
    }

    public LocalDateTime getEndBooking() {
        return endBooking;
    }

    public void setEndBooking(LocalDateTime endBooking) {
        this.endBooking = endBooking;
    }

    public LocalDateTime getStartEvent() {
        return startEvent;
    }

    public void setStartEvent(LocalDateTime startEvent) {
        this.startEvent = startEvent;
    }

    public LocalDateTime getEndEvent() {
        return endEvent;
    }

    public void setEndEvent(LocalDateTime endEvent) {
        this.endEvent = endEvent;
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

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }
}
