package com.backend.EventHub.Controller;

import com.backend.EventHub.Entity.InPersonEvent;
import com.backend.EventHub.Service.InPersonEventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inPersonEvent")
public class InPersonEventController {

    private final InPersonEventService inPersonEventService;

    public InPersonEventController(InPersonEventService inPersonEventService) {
        this.inPersonEventService = inPersonEventService;
    }

    @GetMapping
    public List<InPersonEvent> getAllInPersonEvent(){
        return inPersonEventService.getAllInPersonEvent();
    }
}
