package com.backend.EventHub.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "appuser")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "The name can't be empty or null")
    private String name;

    @NotBlank(message = "The lastname can't be empty or null")
    private String lastname;

    @Column(unique = true)
    @NotBlank(message = "The username can't be empty or null")
    private String username;

    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "The mail have to follow the standard")
    @NotBlank(message = "The email can't be empty or null")
    private String email;

    @NotBlank(message = "The password can't be empty or null")
    private String password;

    @NotBlank(message = "The street can't be empty or null")
    private String street;

    @NotBlank(message = "The zipcode can't be empty or null")
    @Column(name = "zipcode")
    private String zipcode;

    @NotNull(message = "The city can't be null")
    @Min(value = 1, message = "The city id have to be positive")
    private Long city;

    public AppUser() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Long getCity() {
        return city;
    }

    public void setCity(Long city) {
        this.city = city;
    }
}