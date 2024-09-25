package com.backend.EventHub.Entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.web.multipart.MultipartFile;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventRequest {

    private long creator;
    private String title;
    private String description;
    private Integer maxpeople;
    private LocalDateTime startbooking;
    private LocalDateTime endbooking;
    private LocalDateTime startevent;
    private LocalDateTime endevent;
    private BigDecimal price;
    private Boolean isOnline;
    private String url;
    private String street;
    private String zipcode;
    private String city;
    private String nation;

    public EventRequest() {}

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Boolean isOnline) {
        this.isOnline = isOnline;
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
