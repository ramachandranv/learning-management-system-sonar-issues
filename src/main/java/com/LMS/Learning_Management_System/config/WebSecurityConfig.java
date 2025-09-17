package com.LMS.Learning_Management_System.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // Role constants
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_INSTRUCTOR = "INSTRUCTOR";
    private static final String ROLE_STUDENT = "STUDENT";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints (no authentication required)
                        .requestMatchers("/api/auth/login", "/api/auth/signup").permitAll()
                        .requestMatchers("/api/csrf-token").permitAll() // Allow CSRF token retrieval
                        
                        // Admin endpoints - only accessible by admin users
                        .requestMatchers("/api/admin/**").hasRole(ROLE_ADMIN)
                        
                        // Instructor endpoints - accessible by instructors and admins
                        .requestMatchers("/api/instructor/**").hasAnyRole(ROLE_INSTRUCTOR, ROLE_ADMIN)
                        .requestMatchers("/api/course/create", "/api/course/update/**", "/api/course/delete/**").hasAnyRole(ROLE_INSTRUCTOR, ROLE_ADMIN)
                        .requestMatchers("/api/quiz/create/**", "/api/quiz/update/**", "/api/quiz/delete/**").hasAnyRole(ROLE_INSTRUCTOR, ROLE_ADMIN)
                        .requestMatchers("/api/assignment/create/**", "/api/assignment/update/**", "/api/assignment/delete/**").hasAnyRole(ROLE_INSTRUCTOR, ROLE_ADMIN)
                        .requestMatchers("/api/lesson/create/**", "/api/lesson/update/**", "/api/lesson/delete/**").hasAnyRole(ROLE_INSTRUCTOR, ROLE_ADMIN)
                        
                        // Student endpoints - accessible by students and admins
                        .requestMatchers("/api/student/**").hasAnyRole(ROLE_STUDENT, ROLE_ADMIN)
                        .requestMatchers("/api/enrollment/**").hasAnyRole(ROLE_STUDENT, ROLE_ADMIN)
                        
                        // Read-only endpoints - accessible by authenticated users
                        .requestMatchers("/api/course/view/**", "/api/course/list").authenticated()
                        .requestMatchers("/api/quiz/view/**").authenticated()
                        .requestMatchers("/api/assignment/view/**").authenticated()
                        .requestMatchers("/api/lesson/view/**").authenticated()
                        
                        // User management - authenticated users can view/update their own profile
                        .requestMatchers("/api/users/profile", "/api/users/update-profile").authenticated()
                        
                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf
                        // SECURITY REVIEW: HttpOnly=false is intentional for SPA access to CSRF token
                        // This is safe when proper XSS protection is in place on frontend
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        // SECURITY REVIEW: Disabling CSRF for auth endpoints is safe
                        // Login/signup forms can't include CSRF tokens before authentication
                        .ignoringRequestMatchers(
                            "/api/auth/login", 
                            "/api/auth/signup",
                            "/api/auth/logout"
                        )
                        // Note: Frontend should include CSRF token in X-CSRF-TOKEN header
                        // or _csrf parameter for all state-changing requests
                )
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                )
                .sessionManagement(session -> session
                        .sessionFixation().migrateSession()
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                )
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.deny())
                        .contentTypeOptions(contentTypeOptions -> {})
                        .httpStrictTransportSecurity(hsts -> hsts
                                .maxAgeInSeconds(31536000)
                                .includeSubDomains(true)
                        )
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:*", "https://localhost:*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
