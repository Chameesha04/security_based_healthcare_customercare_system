package com.example.healthcare_customercare.config;

import com.example.healthcare_customercare.security.RoleAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.healthcare_customercare.service.AuditLogService;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleAuthorizationFilter roleAuthorizationFilter(AuditLogService auditLogService) {
        return new RoleAuthorizationFilter(auditLogService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            RoleAuthorizationFilter roleAuthorizationFilter
    ) throws Exception {
        http
                .csrf(csrf -> {})
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .logout(logout -> logout.disable())
                .headers(headers -> headers
                        .cacheControl(cache -> {})
                        .frameOptions(frame -> frame.sameOrigin())
                )
                .addFilterBefore(roleAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}