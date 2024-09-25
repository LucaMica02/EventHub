package com.backend.EventHub.Controller;

import com.backend.EventHub.Entity.Nation;
import com.backend.EventHub.Service.NationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nations")
public class NationController {

    private final NationService nationService;

    public NationController(NationService nationService) {
        this.nationService = nationService;
    }

    @GetMapping
    public List<Nation> getAllNation() {
        return nationService.getAllNation();
    }

    @PostMapping
    public ResponseEntity<Nation> createNation(@Valid @RequestBody Nation nation) {
        Nation savedNation = nationService.saveNation(nation);
        return new ResponseEntity<>(savedNation, HttpStatus.CREATED);
    }

}