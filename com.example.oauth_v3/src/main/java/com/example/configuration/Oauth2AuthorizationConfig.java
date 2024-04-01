package com.example.configuration;

import com.example.service.CustomUserDetailsService;
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
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@RequiredArgsConstructor
@EnableAuthorizationServer
@Configuration
public class Oauth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final DataSource dataSource;
    @Bean
    public TokenStore tokenStore() { return new JdbcTokenStore(dataSource); }
    @Bean
    public ApprovalStore approvalStore() { return new JdbcApprovalStore(dataSource); }
    @Bean
    public JdbcAuthorizationCodeServices jdbcAuthorizationCodeServices() { return new JdbcAuthorizationCodeServices(dataSource); }
    @Bean
    public TokenEnhancer tokenEnhancer() { return new CustomTokenEnhancer(); }
//    @Bean // 커스텀해서 사용할듯함 / endpoints에 추가못함
//    public JdbcClientDetailsService jdbcClientDetailsService() { return new JdbcClientDetailsService(dataSource); }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.checkTokenAccess("permitAll()"); // 토큰유효성(/token/check_token) 접근을 위해 설정
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(customUserDetailsService) // refresh token 발행시 유저 정보 검사 하는데 사용하는 서비스 설정
                .tokenStore(tokenStore()) // framework가 자동으로 token 저장
                .approvalStore(approvalStore())
                .authorizationCodeServices(jdbcAuthorizationCodeServices())
                .tokenEnhancer(tokenEnhancer()) // 토큰 발급시 추가정보 입력
        ;
    }
}
