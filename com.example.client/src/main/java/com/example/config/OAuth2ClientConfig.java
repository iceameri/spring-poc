package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

@Configuration
@EnableOAuth2Client
public class OAuth2ClientConfig  {
    @Value("${security.oauth2.client.registration.oauth-client.client-id}")
    private String clientId;
    @Value("${security.oauth2.client.registration.oauth-client.client-secret}")
    private String clientSecret;
    @Value("${security.oauth2.client.registration.oauth-client.redirect-uri}")
    private String redirectUri;
    @Value("${security.oauth2.client.registration.oauth-client.scope}")
    private String scope;

    @Value("${security.oauth2.client.provider.oauth-client.authorization-uri}")
    private String authorizationUri;
    @Value("${security.oauth2.client.provider.oauth-client.token-uri}")
    private String tokenUri;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.defaultClientRegistration());
    }

    private ClientRegistration defaultClientRegistration() {
        return ClientRegistration.withRegistrationId("oauth-client")
                .clientId(this.clientId)
                .clientSecret(this.clientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri(this.redirectUri)
                .scope(this.scope.split(","))
                .authorizationUri(this.authorizationUri)
                .tokenUri(this.tokenUri)
                .userInfoUri("http://localhost:7070/oauth/userinfo")
                .userNameAttributeName(IdTokenClaimNames.SUB)
                .clientName("oauth-client")
                .build();
    }
}