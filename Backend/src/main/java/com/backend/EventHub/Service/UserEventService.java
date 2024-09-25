package com.backend.EventHub.Service;

import com.backend.EventHub.Entity.UserEvent;
import com.backend.EventHub.Repository.UserEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEventService {

    private final UserEventRepository userEventRepository;

    public UserEventService(UserEventRepository userEventRepository) {
        this.userEventRepository = userEventRepository;
    }

    public boolean userEventExist(Long event, Long user) {
        return userEventRepository.existsByEventAndAppUser(event, user);
    }

    public List<UserEvent> getAllUserEvent() {
        return userEventRepository.findAll();
    }

    public UserEvent saveUserEvent(UserEvent userEvent) {
        return userEventRepository.save(userEvent);
    }

    public void deleteUserEvent(Long id) {
        if (userEventRepository.existsById(id)) {
            userEventRepository.deleteById(id);
        } else {
            throw new RuntimeException("UserEvent with id " + id + " not found");
        }
    }
}
