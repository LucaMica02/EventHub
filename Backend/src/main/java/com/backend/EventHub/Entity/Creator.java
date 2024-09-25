package com.backend.EventHub.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Creator")
public class Creator {

    @Id
    @NotNull(message = "The creator have to have a user id")
    @Min(value = 1, message = "The user id have to be positive")
    @Column(name = "appuser")
    private Long appUser;

    @NotBlank(message = "The creator have to have a taxID")
    private String taxID;

    public Creator () {}

    public Long getUser() {
        return appUser;
    }

    public void setUser(Long appUser) {
        this.appUser = appUser;
    }

    public String getTaxID() {
        return taxID;
    }

    public void setTaxID(String taxID) {
        this.taxID = taxID;
    }
}
