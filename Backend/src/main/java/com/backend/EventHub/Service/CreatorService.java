package com.backend.EventHub.Service;

import com.backend.EventHub.Entity.Creator;
import com.backend.EventHub.Repository.CreatorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreatorService {

    private final CreatorRepository creatorRepository;

    public CreatorService(CreatorRepository creatorRepository) {
        this.creatorRepository = creatorRepository;
    }

    public List<Creator> getAllCreator() {
        return creatorRepository.findAll();
    }

    public Creator getCreator(Long id) {
        Optional<Creator> creator = creatorRepository.findById(id);
        if (creator.isEmpty()) {
            throw new RuntimeException("Creator with id: " + id + " not found");
        }
        return creator.get();
    }

    public Creator saveCreator(Creator creator) {
        return creatorRepository.save(creator);
    }

    public Boolean existsById(Long id) {
        return creatorRepository.existsById(id);
    }

    public void deleteCreator(Long id) {
        if (creatorRepository.existsById(id)) {
            creatorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Creator with id " + id + " not found");
        }
    }

    public Creator patchCreator(Long id, String taxID) {
        return creatorRepository.findById(id)
                .map(creator -> {
                    creator.setTaxID(taxID);
                    return creatorRepository.save(creator);
                })
                .orElseThrow(() -> new RuntimeException("Creator with id " + id + " not found"));
    }

    public Integer getTotalStars(Long id) {
        return creatorRepository.getTotalStars(id);
    }

    public Integer getFeedbackCount(Long id) {
        return creatorRepository.getFeedbackCount(id);
    }

    public Integer getEventCount(Long id) {
        return creatorRepository.getEventCount(id);
    }
}
