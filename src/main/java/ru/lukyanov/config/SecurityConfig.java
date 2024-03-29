package ru.lukyanov.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${user.login}")
    private String userLogin;
    @Value("${user.password}")
    private String userPassword;
    @Value("${admin.login}")
    private String adminLogin;
    @Value("${admin.password}")
    private String adminPassword;
    private static final String ROLE_USER = "USER";
    private static final String ROLE_ADMIN = "ADMIN";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/users").hasRole(ROLE_USER)
                        .requestMatchers("/users/{}").hasRole(ROLE_ADMIN)
                        .requestMatchers("/", "/index").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
//                        .loginPage("/login")
                                .defaultSuccessUrl("/users")
                                .permitAll()
                )
                .logout((logout) -> logout.permitAll());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User
                .withUsername(userLogin)
                .password(passwordEncoder.encode(userPassword))
                .roles(ROLE_USER)
                .build();

        UserDetails admin = User
                .withUsername(adminLogin)
                .password(passwordEncoder.encode(adminPassword))
                .roles(ROLE_USER, ROLE_ADMIN)
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//      PasswordEncoder encoder = new BCryptPasswordEncoder(); 
        return encoder;
    }
}
