package com.example.configuration;

import com.example.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebMvcConfig {
    // bean 에 등록하여 userdetail 기본 서비스로 사용
    @Bean
    public UserDetailService userDetailService(){
        return new UserDetailService();
    }
}
