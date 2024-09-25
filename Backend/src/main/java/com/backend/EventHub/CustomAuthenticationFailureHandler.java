package com.backend.EventHub;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        // Log dell'errore
        System.out.println("Autenticazione fallita: " + exception.getMessage());

        // Reindirizza l'utente alla pagina di login con un messaggio di errore
        response.sendRedirect("/login?error=true");
    }
}
