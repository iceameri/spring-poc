package com.example;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OauthTokenDto {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private long expires_in;
    private String scope;
}