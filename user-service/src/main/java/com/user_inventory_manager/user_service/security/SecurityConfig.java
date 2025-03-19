package com.user_inventory_manager.user_service.security;

import com.user_inventory_manager.user_service.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity       // Let spring know use this security filter chain instead of the default
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                    .csrf().disable()
                    .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/api/v1/users/**",
                            "/swagger-ui/**")
                            .permitAll()
                            .anyRequest()
                            .authenticated()
            )
                .httpBasic(withDefaults())  // Login form for REST APIs
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                        .loginPage("/login")
                        .permitAll()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean                    // Authenticate the users by username and password
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // AuthenticationManager by default call the AuthenticationProvider if there is no login endpoint to authenticate
    // But we cant give any inputs such as input credentials to this if we use that default AuthenticationManager.
    // Since we manually handle this, we can give the input to the AuthenticationManager, and it will use the (above) AuthenticationProvider to validate the user.
    // By implementing a Bean the default AuthenticationManager will be not invoked
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();    // PasswordEncoder is an interface, BCryptPasswordEncoder is a class which implements PasswordEncoder
    }
}
