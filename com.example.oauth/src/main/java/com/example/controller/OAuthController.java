package com.example.controller;

import com.example.dto.AccessTokenRequest;
import com.example.dto.OauthTokenResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthController {
    private String clientId = "clintId";
    private String secretKey;
    //    @Value("${oauth.redirectUri}")
    private String redirectUri = "http://localhost:7070/callback";


    // 클라이언트가 구현해야하는 코드 - 발급 받은 코드로 토큰 발행
//    @GetMapping("/callback")
//    public OauthTokenResponse code(@RequestParam String code){
//        AccessTokenRequest request = new AccessTokenRequest(
//                code,
//                "authorization_code",
//                "http://localhost:7070/callback"
//        );
//        String cridentials = "clientId:secretKey";
//        // base 64로 인코딩 (basic auth 의 경우 base64로 인코딩 하여 보내야한다.)
//        String encodingCredentials = new String(
//                Base64.encodeBase64(cridentials.getBytes())
//        );
//        // oauth 서버에 http 통신으로 토큰 발행
//        OauthToken.response oauthToken = Unirest.post("http://localhost:7070/oauth/token")
//                .header("Authorization","Basic "+encodingCredentials)
//                .fields(request.getMapData())
//                .asObject(OauthToken.response.class).getBody();
//        return oauthToken;
//        return null;
//    }
    /*
    * http://localhost:7070/oauth/token
    * -body에 grant_type=authorization_code / redirect_uri=http://localhost:7070/callback / code= {발급받은코드}
     */

    // 클라이언트가 구현해야하는 코드 - 인증 코드 요청
//    @GetMapping("/authorize2")
//    public String authorize() {
//        return "redirect:" + "http://localhost:7070/oauth/authorize?response_type=code&client_id=" + clientId + "&redirect_uri=" + redirectUri;
//    }
//    // 클라이언트가 구현해야하는 코드 - 콜백 URL에서 인증 코드 처리
//    @GetMapping("/callback2")
//    public OauthTokenResponse code2(@RequestParam String code){
//        String credentials = clientId + ":" + secretKey;
//        String encodingCredentials = java.util.Base64.getEncoder().encodeToString(credentials.getBytes());
//
//        AccessTokenRequest request = new AccessTokenRequest(code, "authorization_code", redirectUri);
//
//        HttpResponse<OauthTokenResponse> response;
//        try {
//            response = (HttpResponse<OauthTokenResponse>) Unirest.post("http://localhost:7070/oauth/token")
//                    .header("Authorization", "Basic " + encodingCredentials)
//                    .fields(request.getMapData())
//                    .asObject(OauthTokenResponse.class);
//        } catch (UnirestException e) {
//            e.printStackTrace();
//            return null;
//        }
//
//        return response.body();
//    }
}
