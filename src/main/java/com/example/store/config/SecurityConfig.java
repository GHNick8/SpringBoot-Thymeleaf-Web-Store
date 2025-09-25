package com.example.store.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.store.service.UserService;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public JwtUtils jwtUtils(JwtProperties props) {
        return new JwtUtils(
            props.getSecret(),
            props.getIssuer(),
            Duration.ofMinutes(props.getTtlMinutes())
        );
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter(JwtUtils jwtUtils, UserService users) {
        return new JwtAuthFilter(jwtUtils, users);
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http,
                                      JwtAuthFilter jwtFilter,
                                      JwtUtils jwtUtils) throws Exception {

        http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**", "/cart/**"))
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/**").permitAll()
                /* .requestMatchers("/", "/products/**", "/css/**", "/js/**", "/images/**", "/auth/**").permitAll()
                .requestMatchers("/cart/**").permitAll() 
                .requestMatchers("/api/products/**").permitAll()*/
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/login").permitAll()
                .successHandler((req, res, auth) -> {
                    var token = jwtUtils.generate(auth.getName());
                    res.addHeader("Set-Cookie", jwtUtils.httpOnlyCookie(token));
                    res.sendRedirect("/");
                }))
            .logout(l -> l.logoutUrl("/logout")
                .logoutSuccessHandler((req, res, auth) -> {
                    res.addHeader("Set-Cookie", jwtUtils.clearCookie());
                    res.sendRedirect("/");
                }))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

        return http.build();
    }
}
