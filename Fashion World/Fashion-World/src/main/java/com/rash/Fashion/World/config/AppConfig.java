package com.rash.Fashion.World.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.security.Security;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableWebSecurity

public class AppConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize -> Authorize
                        // 1. Public endpoints
                        .requestMatchers(
                                "/",                    // Home page
                                "/api/v1/auth/**").permitAll() // Allow public access to authentication endpoints

                        // 2. Admin endpoints
                        .requestMatchers("/api/v1/admin/**").hasAnyRole("SHOP OWNER", "ADMIN") //if any api end point starting from api/admin - only the uses having these roles can access admin api endpoints, other users cannot access these

                        // 3. Authenticated endpoints
                        .requestMatchers("/api/**").authenticated() //if any api staring from this,by providing jwt token user will be able to access all these api endpoints, regardless user role

                        // 4. All other requests
                        .anyRequest().permitAll() //all the users can access these endpoints(no matter of roles or no need to have jwt tokens), only permit auth signup & sign in
                )
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }

        private CorsConfigurationSource corsConfigurationSource() {
            return new CorsConfigurationSource(){

                @Override
                public CorsConfiguration getCorsConfiguration(HttpServletRequest request){
                    CorsConfiguration cfg = new CorsConfiguration();

                    cfg.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
                    cfg.setAllowedMethods(Collections.singletonList("*"));
                    cfg.setAllowCredentials(true);
                    cfg.setAllowedHeaders(Collections.singletonList("*"));
                    cfg.setExposedHeaders(Arrays.asList("Authorization"));
                    cfg.setMaxAge(3600L);

                    return cfg;
                }

            };
        }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
