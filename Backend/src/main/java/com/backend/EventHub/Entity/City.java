package com.backend.EventHub.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "City")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The name can't be empty or null")
    private String name;

    @NotNull(message = "The city have to have in a nation")
    @Min(value = 1, message = "The nation id have to be positive")
    private Long nation;

    public City() {}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNation() {
        return nation;
    }

    public void setNation(Long nation) {
        this.nation = nation;
    }
}