package com.ads.report.domain.oauth2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuth2AuthorizedClientDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("clientRegistrationId")
    private String clientRegistrationId;

    @JsonProperty("principalName")
    private String principalName;

    @JsonProperty("accessToken")
    private String accessToken;

    @JsonProperty("accessTokenExpiresAt")
    private long accessTokenExpiresAt;

    @JsonProperty("refreshToken")
    private String refreshToken;

    public OAuth2AuthorizedClientDto(String clientRegistrationId, String principalName, String accessToken, long accessTokenExpiresAt, String refreshToken) {
        this.clientRegistrationId = clientRegistrationId;
        this.principalName = principalName;
        this.accessToken = accessToken;
        this.accessTokenExpiresAt = accessTokenExpiresAt;
        this.refreshToken = refreshToken;
    }

    public String getClientRegistrationId() {
        return clientRegistrationId;
    }

    public void setClientRegistrationId(String clientRegistrationId) {
        this.clientRegistrationId = clientRegistrationId;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getAccessTokenExpiresAt() {
        return accessTokenExpiresAt;
    }

    public void setAccessTokenExpiresAt(long accessTokenExpiresAt) {
        this.accessTokenExpiresAt = accessTokenExpiresAt;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
