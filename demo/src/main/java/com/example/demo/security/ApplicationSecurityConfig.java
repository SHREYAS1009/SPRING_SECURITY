package com.example.demo.security;

import com.example.demo.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import static org.springframework.security.config.Customizer.withDefaults;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
@Configuration
@EnableWebSecurity


public class ApplicationSecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //to learn later
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "index", "/css/*", "/js/*").permitAll()
                        // Allow public access to specified URLs
                        .requestMatchers("/api/**").hasRole(String.valueOf(ApplicationUserRole.ADMIN))
                        .requestMatchers(HttpMethod.DELETE,"/management/api/**").hasRole()
                        .anyRequest().authenticated()  // All other requests require authentication
                )
                .httpBasic(withDefaults());  // Use HTTP Basic Authentication

        return http.build();


    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public UserDetailsService userDetailsService() {
        // Create in-memory users for testing (this is just an example)


        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))  // {noop} means no password encoding (only for testing purposes!)
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build();


        UserDetails administrator = User.builder()
                .username("admins")
                .password(passwordEncoder.encode("admin123"))  // {noop} means no password encoding (only for testing purposes!)
                .roles("ADMINISTRATOR")
                .build();



        return new InMemoryUserDetailsManager(admin,user,administrator);  // Use InMemoryUserDetailsManager for simple user storage


    }
}
