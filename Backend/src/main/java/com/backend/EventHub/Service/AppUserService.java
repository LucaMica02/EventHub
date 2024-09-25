package com.backend.EventHub.Service;

import com.backend.EventHub.Entity.AppUser;
import com.backend.EventHub.Repository.AppUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public Boolean existsById(Long id) {
        return appUserRepository.existsById(id);
    }

    public List<AppUser> getAllUser() {
        return appUserRepository.findAll();
    }

    public Optional<AppUser> getUserById(Long id) {
        return appUserRepository.findById(id);
    }

    public AppUser saveUser(AppUser user) {
        return appUserRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (appUserRepository.existsById(id)) {
            appUserRepository.deleteById(id);
        } else {
            throw new RuntimeException("User with id " + id + " not found");
        }
    }

    public AppUser patchUser(Long id, Map<String, String> updates){
        return appUserRepository.findById(id)
                .map(user -> {
                    updates.forEach((key, value) -> {
                        switch (key) {
                            case "username":
                                user.setUsername(value);
                                break;
                            case "email":
                                user.setEmail(value);
                                break;
                            case "password":
                                user.setPassword(value);
                                break;
                            case "street":
                                user.setStreet(value);
                                break;
                            case "zipcode":
                                user.setZipcode(value);
                                break;
                            case "city":
                                user.setCity(Long.parseLong(value));
                                break;
                            default:
                                throw new IllegalArgumentException("Field not allowed: " + key);
                        }
                    });
                    return appUserRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }
}
