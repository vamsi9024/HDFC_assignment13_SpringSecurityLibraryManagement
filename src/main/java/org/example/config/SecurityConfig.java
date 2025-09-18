package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN").build();

        UserDetails librarian = User.withUsername("librarian")
                .password(passwordEncoder().encode("lib123"))
                .roles("LIBRARIAN").build();

        UserDetails student = User.withUsername("student")
                .password(passwordEncoder().encode("student123"))
                .roles("STUDENT").build();

        UserDetails guest = User.withUsername("guest")
                .password(passwordEncoder().encode("guest123"))
                .roles("GUEST").build();
        return new InMemoryUserDetailsManager(admin,librarian,student,guest);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // if you need to disable CSRF for easier testing with curl/Postman
                .csrf(csrf -> csrf.disable())

                // the new DSL entry point
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/about", "/login", "/books/public", "/css/**").permitAll()
                        .requestMatchers("/books/**").hasAnyRole("STUDENT", "LIBRARIAN", "ADMIN")
                        .requestMatchers("/reservations/**").hasAnyRole("LIBRARIAN", "ADMIN")
                        .requestMatchers("/users", "/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                )

                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied")
                );

        return http.build();
    }
    

}



