package com.backend.EventHub;

import com.backend.EventHub.Entity.AppUser;
import com.backend.EventHub.Repository.AppUserRepository;
import com.backend.EventHub.Repository.CreatorRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final CreatorRepository creatorRepository;

    public CustomUserDetailService (AppUserRepository appUserRepository, CreatorRepository creatorRepository) {
        this.appUserRepository = appUserRepository;
        this.creatorRepository = creatorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        System.out.println("AppUser Found: " + appUser);

        if (appUser == null) {
            System.out.println("AppUser Found: " + appUser + " is null");
            throw new UsernameNotFoundException("Username not found");
        }
        String role = creatorRepository.existsByAppUser(appUser.getId()) ? "CREATOR" : "USER";
        System.out.println("AppUser Role: " + role);

        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(role)
                .build();
    }
}
