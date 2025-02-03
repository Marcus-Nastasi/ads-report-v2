package com.ads.report.infrastructure.configuration.sheets;

import com.ads.report.application.gateway.sheets.GoogleSheetsGateway;
import com.ads.report.application.usecases.sheets.GoogleSheetsUseCase;
import com.ads.report.infrastructure.exception.ForbiddenException;
import com.ads.report.infrastructure.gateway.sheets.GoogleSheetsRepoGateway;
import com.ads.report.infrastructure.gateway.redis.RedisOAuth2AuthorizedClient;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.UserCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.context.annotation.RequestScope;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

/**
 * The configuration of Google Sheets.
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
@Configuration
public class GoogleSheetsConfiguration {

    @Value("${api.googleads.clientId}")
    private String clientId;
    @Value("${api.googleads.clientSecret}")
    private String clientSecret;
    @Value("${api.googleads.developerToken}")
    private String developerToken;

    /**
     *
     * <p>Google Sheets client bean using OAuth2 dynamic authentication.<p/>
     *
     * @return A dynamically authenticated Sheets client.
     * @throws IOException If fails to authenticate.
     */
    @Bean
    @RequestScope
    public Sheets googleSheetsService(RedisOAuth2AuthorizedClient authorizedClientService,
                                      OAuth2AuthorizedClientManager authorizedClientManager) {
        // Getting authentication from session security context holder.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Tests if the authentication granted is an OAuth2 instance, and associate it to oauthToken variable.
        if (!(authentication instanceof OAuth2AuthenticationToken oauthToken)) {
            throw new ForbiddenException("User is not authenticated on Google OAuth2.");
        }
        // Loading authorized client by token's information.
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
            oauthToken.getAuthorizedClientRegistrationId(),
            oauthToken.getName()
        );
        if (client == null) {
            throw new ForbiddenException("OAuth2AuthorizedClient not found. The user may be not authenticated.");
        }
        // Automatically renew token if expired.
        client = authorizedClientManager.authorize(OAuth2AuthorizeRequest
            .withClientRegistrationId(oauthToken.getAuthorizedClientRegistrationId())
            .principal(oauthToken)
            .build());
        if (client == null || client.getAccessToken() == null) {
            throw new ForbiddenException("Failed to renew access_token. Logon again.");
        }
        // Getting final access_token, expiration time and refresh_token.
        String accessToken = client.getAccessToken().getTokenValue();
        Instant expiresAt = client.getAccessToken().getExpiresAt();
        String refreshToken = (client.getRefreshToken() != null) ? client.getRefreshToken().getTokenValue() : "";
        if (refreshToken.isEmpty()) throw new ForbiddenException("refresh_token not found.");
        Date date = new Date();
        date.setTime(expiresAt.getEpochSecond());
        // Using UserCredentials to support the token's automatic renew.
        UserCredentials credentials = UserCredentials.newBuilder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setAccessToken(new AccessToken(accessToken, date))
            .setRefreshToken(refreshToken)
            .build();
        // Creating Google Sheets client.
        return new Sheets.Builder(
            new NetHttpTransport(),
            new GsonFactory(),
            new HttpCredentialsAdapter(credentials)
        ).setApplicationName("Ads Report").build();
    }

    @Bean
    public GoogleSheetsRepoGateway googleSheetsRepoGateway() {
        return new GoogleSheetsRepoGateway();
    }

    @Bean
    public GoogleSheetsUseCase googleSheetsUseCase(GoogleSheetsGateway googleSheetsGateway) {
        return new GoogleSheetsUseCase(googleSheetsGateway);
    }
}
