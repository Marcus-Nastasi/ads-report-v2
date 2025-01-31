package com.ads.report.infrastructure.gateway.redis;

import com.ads.report.domain.oauth2.OAuth2AuthorizedClientDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class RedisOAuth2AuthorizedClient implements OAuth2AuthorizedClientService {

    private final RedisTemplate<String, OAuth2AuthorizedClientDto> redisTemplate;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private static final String REDIS_KEY_PREFIX = "oauth2:client:";

    public RedisOAuth2AuthorizedClient(RedisTemplate<String, OAuth2AuthorizedClientDto> redisTemplate, ClientRegistrationRepository clientRegistrationRepository) {
        this.redisTemplate = redisTemplate;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        String key = REDIS_KEY_PREFIX + principal.getName();
        OAuth2AuthorizedClientDto dto = new OAuth2AuthorizedClientDto(
            authorizedClient.getClientRegistration().getRegistrationId(),
            principal.getName(),
            authorizedClient.getAccessToken().getTokenValue(),
            authorizedClient.getAccessToken().getExpiresAt().toEpochMilli(),
            authorizedClient.getRefreshToken() != null ? authorizedClient.getRefreshToken().getTokenValue() : null
        );
        redisTemplate.opsForValue().set(key, dto);
    }

    @Override
    public OAuth2AuthorizedClient loadAuthorizedClient(String clientRegistrationId, String principalName) {
        String key = REDIS_KEY_PREFIX + principalName;
        OAuth2AuthorizedClientDto dto = redisTemplate.opsForValue().get(key);
        if (dto == null) {
            return null;
        }
        // Reconstruindo OAuth2AuthorizedClient manualmente
        OAuth2AccessToken accessToken = new OAuth2AccessToken(
            OAuth2AccessToken.TokenType.BEARER,
            dto.getAccessToken(),
            Instant.now(),
            Instant.ofEpochMilli(dto.getAccessTokenExpiresAt())
        );

        OAuth2RefreshToken refreshToken = dto.getRefreshToken() != null
            ? new OAuth2RefreshToken(dto.getRefreshToken(), Instant.now())
            : null;

        return new OAuth2AuthorizedClient(
            clientRegistrationRepository.findByRegistrationId(dto.getClientRegistrationId()),
            dto.getPrincipalName(),
            accessToken,
            refreshToken
        );
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        String key = REDIS_KEY_PREFIX + principalName;
        redisTemplate.delete(key);
    }
}
