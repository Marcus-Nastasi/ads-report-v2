package com.ads.report.infrastructure.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * The security configuration class.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Value("${api.googleads.clientId}")
    private String clientId;
    @Value("${api.googleads.clientSecret}")
    private String clientSecret;

    /**
     *
     * <p>Security filter chain configuration.<p/>
     *
     * @param http A HttpSecurity type object.
     * @return The security filter chain modified.
     *
     * @throws Exception If fails to configure the security filter chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/", "/login").permitAll()
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                    .anyRequest().authenticated();
                }
            ).oauth2Login(login -> {
                Customizer.withDefaults();
                login.defaultSuccessUrl("/swagger-ui/index.html#/");
            });
        return http.build();
    }

    /**
     *
     * <p>Configuration of the details of authentication, like scopes, URIs, redirection, etc.<p/>
     *
     * @return A new object of ClientRegistrationRepository, with the details.
     */
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration googleClientRegistration = ClientRegistration
            .withRegistrationId("google")
            .clientId(clientId)
            .clientSecret(clientSecret)
            .scope("openid", "profile", "email", "https://www.googleapis.com/auth/adwords", "https://www.googleapis.com/auth/spreadsheets")
            .authorizationUri("https://accounts.google.com/o/oauth2/auth?access_type=offline&prompt=consent")
            .tokenUri("https://oauth2.googleapis.com/token")
            .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
            .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
            .userNameAttributeName("name")
            .redirectUri("{baseUrl}/login/oauth2/code/google")
            .clientName("Google")
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .build();
        return new InMemoryClientRegistrationRepository(googleClientRegistration);
    }
}
