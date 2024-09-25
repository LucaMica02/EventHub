package com.backend.EventHub.Controller;

import com.backend.EventHub.Entity.OnlineEvent;
import com.backend.EventHub.Service.OnlineEventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/onlineEvent")
public class OnlineEventController {

    private final OnlineEventService onlineEventService;

    public OnlineEventController(OnlineEventService onlineEventService) {
        this.onlineEventService = onlineEventService;
    }

    @GetMapping
    public List<OnlineEvent> getAllOnlineEvent() {
        return onlineEventService.getAllOnlineEvent();
    }

}
