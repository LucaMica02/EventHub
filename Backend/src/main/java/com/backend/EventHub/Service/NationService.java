package com.backend.EventHub.Service;

import com.backend.EventHub.Entity.Nation;
import com.backend.EventHub.Repository.NationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NationService {

    private final NationRepository nationRepository;

    public NationService(NationRepository nationRepository) {
        this.nationRepository = nationRepository;
    }

    public List<Nation> getAllNation() {
        return nationRepository.findAll();
    }

    public Nation saveNation(Nation nation) {
        return nationRepository.save(nation);
    }

    public Long getNationId(String name) {
        if (nationRepository.existsByName(name)) {
            return nationRepository.getByName(name).getId();
        }

        Nation nation = new Nation();
        nation.setName(name);
        nationRepository.save(nation);
        return nation.getId();
    }
}
