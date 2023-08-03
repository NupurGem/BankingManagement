package com.example.BankingManagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager()
    {
        UserDetails aman = User.withUsername("Aman")
                .password("{noop}test123")
                .roles("USER","MANAGER")
                .build();

        UserDetails harsh= User.withUsername("Harsh")
                .password("{noop}test123")
                .roles("USER")
                .build();

        UserDetails sagar = User.withUsername("Sagar")
                .password("{noop}test123")
                .roles("USER","MANAGER","ADMIN")
                .build();
    
        return new InMemoryUserDetailsManager(aman,harsh,sagar);
    }

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurer-> configurer.anyRequest().authenticated());
        return null;
    }


}
