package com.backend.EventHub.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "onlineevent")
public class OnlineEvent {

    @Id
    @Min(value = 1, message = "The event id have to be positive")
    @Column(name = "event")
    private Long id;

    @NotBlank(message = "The url can't be null or empty")
    @Pattern(regexp = "^https?:\\//[^\\s\\/$.?#].[^\\s]*$", message = "The url have to respect the pattern")
    private String url;

    public OnlineEvent() {}

    public Long getEvent() {
        return id;
    }

    public void setEvent(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}