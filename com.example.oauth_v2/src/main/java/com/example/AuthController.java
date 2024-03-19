package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Value("${oauth2.client_id}")
    private String oauth2ClientId;
    @Value("${oauth2.secret}")
    private String oauth2Secret;

    // GET 으로 했던 인증을 POST로 변경하면서 커스텀
    @PostMapping("/oauth/authorize")
    public String oauthAuthorize(@RequestBody OauthAuthorizeRequest request) {
        if (request.getClientId().equals(oauth2ClientId) == false) {
            return "FAILED";
        }
        if(request.getSecret().equals(oauth2Secret) == false) {
            return "FAILED";
        }

        String credentials = request.getClientId()+":"+request.getSecret();
        String encodedCredentials = new String(Base64.encode(credentials.getBytes()));
        return "Basic " + encodedCredentials;
    }
}
