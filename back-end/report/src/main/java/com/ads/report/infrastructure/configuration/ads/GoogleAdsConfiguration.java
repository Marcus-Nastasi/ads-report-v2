package com.ads.report.infrastructure.configuration.ads;

import com.ads.report.adapters.mappers.GoogleAdsDtoMapper;
import com.ads.report.application.gateway.ads.GoogleAdsGateway;
import com.ads.report.application.usecases.ads.GoogleAdsUseCase;
import com.ads.report.infrastructure.exception.ForbiddenException;
import com.ads.report.infrastructure.gateway.ads.GoogleAdsRepoGateway;
import com.ads.report.infrastructure.gateway.redis.RedisOAuth2AuthorizedClient;
import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.UserCredentials;
import com.google.gson.Gson;
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
 *
 * The Google Ads configuration class.
 *
 * <p>Here we create the adwords beans..<p/>
 *
 * @author Marcus Nastasi
 * @version 1.0.1
 * @since 2025
 * */
@Configuration
public class GoogleAdsConfiguration {

    @Value("${api.googleads.clientId}")
    private String clientId;
    @Value("${api.googleads.clientSecret}")
    private String clientSecret;
    @Value("${api.googleads.developerToken}")
    private String developerToken;

    /**
     *
     * Bean that generates the Google Ads client.
     *
     * @param authorizedClientService An implementations of OAuth2AuthorizedClientService for server states config.
     * @param authorizedClientManager An OAuth2AuthorizedClientManager to manage the client logged on.
     *
     * @return Return the adwords client based on the OAuth2.0 login.
     */
    @Bean
    @RequestScope
    public GoogleAdsClient googleAdsClient(RedisOAuth2AuthorizedClient authorizedClientService,
                                           OAuth2AuthorizedClientManager authorizedClientManager) {
        // Getting authentication from session security context holder.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Tests if the authentication granted is an OAuth2 instance, and associate it to oauthToken variable.
        if (!(authentication instanceof OAuth2AuthenticationToken oauthToken))
            throw new ForbiddenException("User is not authenticated on Google OAuth2.");
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
        if (client == null || client.getAccessToken() == null)
            throw new ForbiddenException("Failed to renew access_token. Logon again.");
        // Getting final access_token, expiration time and refresh_token.
        String accessToken = client.getAccessToken().getTokenValue();
        Instant expiresAt = client.getAccessToken().getExpiresAt();
        String refreshToken = (client.getRefreshToken() != null) ? client.getRefreshToken().getTokenValue() : "";
        if (refreshToken.isEmpty()) throw new ForbiddenException("refresh_token not found.");
        Date date = new Date();
        date.setTime(expiresAt.getEpochSecond());
        // Creating OAuth2 credentials for GoogleAds.
        UserCredentials credentials = UserCredentials.newBuilder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setAccessToken(new AccessToken(accessToken, date))
            .setRefreshToken(refreshToken)
            .build();
        // Creating GoogleAds client.
        return GoogleAdsClient.newBuilder()
            .setDeveloperToken(developerToken)
            .setCredentials(credentials)
            .build();
    }

    @Bean
    public GoogleAdsGateway googleAdsGateway() {
        return new GoogleAdsRepoGateway();
    }

    @Bean
    public GoogleAdsUseCase googleAdsUseCase(GoogleAdsGateway googleAdsGateway) {
        return new GoogleAdsUseCase(googleAdsGateway);
    }

    @Bean
    public GoogleAdsDtoMapper googleAdsDtoMapper() {
        return new GoogleAdsDtoMapper();
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }
}
