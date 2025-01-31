package com.ads.report.infrastructure.gateway.oauth2;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

import java.io.IOException;
import java.time.Instant;

public class OAuth2AuthorizedClientDeserializer extends StdDeserializer<OAuth2AuthorizedClient> {

    private final ClientRegistrationRepository clientRegistrationRepository;

    public OAuth2AuthorizedClientDeserializer(ClientRegistrationRepository clientRegistrationRepository) {
        super(OAuth2AuthorizedClient.class);
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Override
    public OAuth2AuthorizedClient deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        String clientRegistrationId = node.get("clientRegistrationId").asText();
        String accessTokenValue = node.get("accessToken").asText();
        Instant accessTokenExpiresAt = Instant.ofEpochMilli(node.get("accessTokenExpiresAt").asLong());
        OAuth2AccessToken accessToken = new OAuth2AccessToken(
            OAuth2AccessToken.TokenType.BEARER,
            accessTokenValue,
            Instant.now(),
            accessTokenExpiresAt
        );
        OAuth2RefreshToken refreshToken = node.has("refreshToken")
            ? new OAuth2RefreshToken(node.get("refreshToken").asText(), Instant.now())
            : null;
        return new OAuth2AuthorizedClient(clientRegistrationRepository
            .findByRegistrationId(clientRegistrationId),
            clientRegistrationId,
            accessToken,
            refreshToken);
    }
}
