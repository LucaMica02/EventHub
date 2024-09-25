package com.backend.EventHub.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "userevent")
public class UserEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The event id can't be null")
    @Min(value = 1, message = "The event id have to be positive")
    private Long event;

    @NotNull(message = "The user id can't be null")
    @Min(value = 1, message = "The user id have to be positive")
    @Column(name = "appuser")
    private Long appUser;

    public UserEvent() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEvent() {
        return event;
    }

    public void setEvent(Long event) {
        this.event = event;
    }

    public Long getUser() {
        return appUser;
    }

    public void setUser(Long appUser) {
        this.appUser = appUser;
    }
}