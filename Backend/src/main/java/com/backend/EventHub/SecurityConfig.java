package com.backend.EventHub;

import com.backend.EventHub.Repository.AppUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailService customUserDetailService;
    private final AppUserRepository appUserRepository;

    public SecurityConfig (CustomUserDetailService customUserDetailService, AppUserRepository appUserRepository) {
        this.customUserDetailService = customUserDetailService;
        this.appUserRepository = appUserRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/event/**").permitAll()
                                .requestMatchers("/api/creator/**").permitAll()
                                .requestMatchers("/api/appUser/register/**").permitAll()
                                .requestMatchers("/api/images/**").permitAll()
                                .requestMatchers("/api/feedback/get_by_creator/**").permitAll()
                                .requestMatchers("/api/event/create").hasRole("CREATOR")
                                .anyRequest().hasAnyRole("CREATOR", "USER")
                )
                .formLogin() // Abilita il login tramite form
                //.loginProcessingUrl("/login") // Assicura che Spring gestisca le richieste di login a /login
                .usernameParameter("username") // Parametro per username
                .passwordParameter("password") // Parametro per password
                .successHandler(new CustomAuthenticationSuccessHandler(appUserRepository))
                .failureHandler(new CustomAuthenticationFailureHandler())
                .permitAll()
                .and()
                .logout(logout -> logout
                        .logoutUrl("/logout") // Configura il percorso di logout
                        .logoutSuccessUrl("/login?logout=true") // Reindirizza dopo un logout riuscito
                        .invalidateHttpSession(true) // Invalidare la sessione
                        .deleteCookies("JSESSIONID") // Cancella i cookie di sessione
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailService;
    }

    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.parentAuthenticationManager(null);
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        // Set parent authentication manager to null to avoid circular references
        authenticationManagerBuilder.parentAuthenticationManager(null);

        // Set user details service and password encoder
        authenticationManagerBuilder
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }
}