package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${oauth2.client_id}") // client id. 프로퍼티에 등록
    private String oauth2ClientId;

    @Value("${oauth2.secret}")  // secret. 프로퍼티에 등록
    private String oauth2Secret;

    private AuthenticationManager authenticationManager;

    private DataSource dataSource;

    public AuthorizationServerConfig(AuthenticationConfiguration authenticationConfiguration, DataSource dataSource) throws Exception {
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.dataSource = dataSource;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        endpoints.tokenStore(jdbcTokenStore());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(oauth2ClientId)
                .secret("{noop}" + oauth2Secret)
                .autoApprove(true)
                .redirectUris("/oauth2/callback")
                .scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token")
        ;
    }

    public TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }
}
