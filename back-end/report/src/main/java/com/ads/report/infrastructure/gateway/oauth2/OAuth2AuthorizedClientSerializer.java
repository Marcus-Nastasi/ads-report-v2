package com.ads.report.infrastructure.gateway.oauth2;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;

import java.io.IOException;

public class OAuth2AuthorizedClientSerializer extends StdSerializer<OAuth2AuthorizedClient> {

    public OAuth2AuthorizedClientSerializer() {
        super(OAuth2AuthorizedClient.class);
    }

    @Override
    public void serialize(OAuth2AuthorizedClient value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("clientRegistrationId", value.getClientRegistration().getRegistrationId());
        gen.writeStringField("accessToken", value.getAccessToken().getTokenValue());
        gen.writeNumberField("accessTokenExpiresAt", value.getAccessToken().getExpiresAt().toEpochMilli());
        if (value.getRefreshToken() != null) {
            gen.writeStringField("refreshToken", value.getRefreshToken().getTokenValue());
        }
        gen.writeEndObject();
    }
}
