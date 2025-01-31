package com.ads.report.adapters.resources;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v2/auth")
public class OAuth2Resource {

    private final OAuth2AuthorizedClientService authorizedClientService;

    public OAuth2Resource(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

    @GetMapping("/user")
    public Map<String, Object> getUser(@AuthenticationPrincipal OidcUser user, OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient authorizedClient = authorizedClientService
            .loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
        Map<String, Object> response = new HashMap<>(user.getClaims());
        response.put("access_token", authorizedClient.getAccessToken().getTokenValue());
        response.put(
            "refresh_token",
            authorizedClient.getRefreshToken() != null ? authorizedClient.getRefreshToken().getTokenValue() : "No refresh token"
        );
        return response;
    }
}
