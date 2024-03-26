package com.example.configuration;

import com.example.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@RequiredArgsConstructor
@EnableAuthorizationServer
@Configuration
public class Oauth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
    private final UserDetailService userDetailService;
    private final AuthenticationManager authenticationManager;
    private final DataSource dataSource;
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }
    @Bean
    public ApprovalStore approvalStore() { return new JdbcApprovalStore(dataSource); }
    @Bean
    public JdbcAuthorizationCodeServices jdbcAuthorizationCodeServices() { return new JdbcAuthorizationCodeServices(dataSource); }

    @Bean // 커스텀해서 사용할듯함
    public JdbcClientDetailsService jdbcClientDetailsService() { return new JdbcClientDetailsService(dataSource); }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.checkTokenAccess("permitAll()");
    }

    // client 설정
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    // 인증, 토큰 설정
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailService) // refresh token 발행시 유저 정보 검사 하는데 사용하는 서비스 설정
                .tokenStore(tokenStore())
                .approvalStore(approvalStore())
                .authorizationCodeServices(jdbcAuthorizationCodeServices())
        ;
    }
}
