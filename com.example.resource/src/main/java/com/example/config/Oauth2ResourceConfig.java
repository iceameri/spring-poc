package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@EnableResourceServer
@Configuration
public class Oauth2ResourceConfig extends ResourceServerConfigurerAdapter {
    // API 별 필요한 인증정보 설정
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/ping").permitAll()
                .anyRequest().authenticated();
    }

    // 토큰 유효성 체크
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        RemoteTokenServices remoteTokenService = new RemoteTokenServices();
        remoteTokenService.setClientId("clientId");
        remoteTokenService.setClientSecret("secretKey");
        remoteTokenService.setCheckTokenEndpointUrl("http://localhost:7070/oauth/check_token");
        resources.tokenServices(remoteTokenService);
    }
}

