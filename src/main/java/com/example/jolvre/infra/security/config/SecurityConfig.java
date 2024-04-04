package com.example.jolvre.infra.security.config;

import com.example.jolvre.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> {
                            request.requestMatchers("/oauth2/authorization/google", "/", "/css/**", "/images/**", "/js/**",
                                    "/oauth/**", "/aa/**", "/aa").permitAll();
                            request.requestMatchers("/api/v1/**", "/api/v1/aa").hasRole(Role.USER.name());
                            request.anyRequest().authenticated();
                        }
                )
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .oauth2Login(oauth -> oauth
                        .loginPage("/oauth2/authorization/google")
                        .failureUrl("/oauth2/authorization/google")
                        .defaultSuccessUrl("/aa", true)
                        .successHandler(new MyAuthenticationSuccessHandler())
                        .userInfoEndpoint(userEndPoint -> userEndPoint.userService(customOAuth2UserService))
                );

        return http.build();

    }
}
