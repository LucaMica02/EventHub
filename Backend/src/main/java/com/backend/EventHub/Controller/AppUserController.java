package com.backend.EventHub.Controller;

import com.backend.EventHub.Entity.*;
import com.backend.EventHub.Repository.CityRepository;
import com.backend.EventHub.Service.*;
import com.backend.EventHub.SecurityConfig;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/appUser")
public class AppUserController {

    private final AppUserService appUserService;
    private final CreatorService creatorService;
    private final EventService eventService;
    private final NationService nationService;
    private final CityService cityService;
    private final EventController eventController;
    private final PasswordEncoder passwordEncoder;

    public AppUserController(AppUserService appUserService, CreatorService creatorService, EventService eventService, NationService nationService, CityService cityService, EventController eventController, PasswordEncoder passwordEncoder) {
        this.appUserService = appUserService;
        this.creatorService = creatorService;
        this.eventService = eventService;
        this.nationService = nationService;
        this.cityService = cityService;
        this.eventController = eventController;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<AppUser> getAllUser() {
        return appUserService.getAllUser();
    }

    @GetMapping("/{id}/events")
    public ResponseEntity<List<EventResponse>> getEventsByUserId(@PathVariable Long id) {
        List<Event> events;
        if (!appUserService.existsById(id)) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        if (creatorService.existsById(id)) {
            events = eventService.getEventsByCreatorId(id);
        } else {
            events = eventService.getEventsByUserId(id);
        }
        List<EventResponse> eventResponses = new ArrayList<EventResponse>();
        for (Event e: events) {
            eventResponses.add(eventController.helper(e));
        }
        return new ResponseEntity<>(eventResponses, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRequest> getUserById(@PathVariable Long id) {
        Optional<AppUser> user = appUserService.getUserById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        AppUser appUser = user.get();
        UserRequest userRequest = new UserRequest();
        userRequest.setId(appUser.getId());
        userRequest.setName(appUser.getName());
        userRequest.setLastname(appUser.getLastname());
        userRequest.setUsername(appUser.getUsername());
        userRequest.setEmail(appUser.getEmail());
        userRequest.setStreet(appUser.getStreet());
        userRequest.setZipcode(appUser.getZipcode());

        userRequest.setCity(cityService.getCityById(appUser.getCity()));
        userRequest.setNation(cityService.getNationNameByCityId(appUser.getCity()));
        userRequest.setIsCreator(creatorService.existsById(appUser.getId()));
        if (userRequest.getIsCreator() == Boolean.TRUE) {
            userRequest.setTaxId(creatorService.getCreator(appUser.getId()).getTaxID());
        } else {
            userRequest.setTaxId(null);
        }

        return new ResponseEntity<>(userRequest, HttpStatus.ACCEPTED); // return Http status with code = 202
    }

    @PostMapping("/register")
    public ResponseEntity<AppUser> createUser(@Valid @RequestBody UserRequest user) {
        AppUser appUser = new AppUser();

        appUser.setName(user.getName());
        appUser.setLastname(user.getLastname());
        appUser.setUsername(user.getUsername());
        appUser.setEmail(user.getEmail());
        appUser.setPassword(passwordEncoder.encode(user.getPassword()));
        appUser.setStreet(user.getStreet());
        appUser.setZipcode(user.getZipcode());

        Long nationId = nationService.getNationId(user.getNation());
        Long cityId = cityService.getCityId(user.getCity(), nationId);
        appUser.setCity(cityId);
        AppUser savedUser = appUserService.saveUser(appUser);

        if (user.getIsCreator() == Boolean.TRUE) {
            Creator creator = new Creator();
            creator.setUser(savedUser.getId());
            creator.setTaxID(user.getTaxId());
            creatorService.saveCreator(creator);
        }

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        appUserService.deleteUser(id);
        return new ResponseEntity<>("User with id " + id + " deleted successfully", HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AppUser> patchUser(@PathVariable Long id, @RequestBody Map<String, String> updates){
        try {
            AppUser updatedUser = appUserService.patchUser(id, updates);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
