package edu.cit.cordova.johannanthony.campusequipmentload.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.web.servlet.function.RequestPredicates.headers;

@Configuration
public class SecurityConfig {

    // 🔐 Password encoder (hash passwords with BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 🔐 Security filter chain (request rules)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for Postman/React
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/student/register").permitAll() // allow registration
                        .requestMatchers("/h2-console/**").permitAll() // allow H2 console
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginProcessingUrl("/login") // Spring Security handles POST /login
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .headers(headers ->
                        headers.frameOptions(frameOptions -> frameOptions.disable()) // ✅ new style
                );

        return http.build();
    }

    // 🔐 Authentication manager (needed if you want to manually authenticate somewhere else)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
