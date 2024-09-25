package com.backend.EventHub;

import com.backend.EventHub.Entity.AppUser;
import com.backend.EventHub.Repository.AppUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Optional;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AppUserRepository appUserRepository;
    private String redirectUrl;

    public CustomAuthenticationSuccessHandler(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        AppUser user = appUserRepository.findByUsername(authentication.getName());
        if (user == null) {
            setRedirectUrl("/api/event");
        } else {
            setRedirectUrl("/api/appUser/" + user.getId());
        }
        response.sendRedirect(this.redirectUrl);
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}