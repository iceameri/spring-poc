package com.example.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/***
 * 액세스 토큰 요청 정보
 */
@Data
public class AccessTokenRequest {
    private String code;
    private String grant_type;
    private String redirect_uri;

    public AccessTokenRequest(String code, String grant_type, String redirectUri) {
        this.code = code;
        this.grant_type = grant_type;
        this.redirect_uri = redirectUri;
    }

    public Map<String, Object> getMapData() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("grant_type", grant_type);
        map.put("redirect_uri", redirect_uri);
        return map;
    }
}
