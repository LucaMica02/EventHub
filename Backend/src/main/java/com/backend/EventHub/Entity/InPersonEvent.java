package com.backend.EventHub.Entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "inpersonevent")
public class InPersonEvent {

    @Id
    @NotNull(message = "The event id can't be null")
    @Min(value = 1, message = "The event id have to be positive")
    private Long event;

    @NotBlank(message = "The street can't be empty or null")
    private String street;

    @NotBlank(message = "The zipcode can't be empty or null")
    private String zipcode;

    @NotNull(message = "The city id can't be empty")
    @Min(value = 1, message = "The city id have to be positive")
    private Long city;

    public InPersonEvent() {}

    public Long getEvent() {
        return event;
    }

    public void setEvent(Long event) {
        this.event = event;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipcode;
    }

    public void setZipCode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }
}