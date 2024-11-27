package com.example.controller;

import com.example.domain.AccessTokenRequest;
import com.example.domain.OauthTokenResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class TestController {
    @Value("${security.oauth2.client.registration.oauth-client.client-id}")
    private String clientId;
    @Value("${security.oauth2.client.registration.oauth-client.client-secret}")
    private String clientSecret;
    @Value("${security.oauth2.client.registration.oauth-client.redirect-uri}")
    private String redirectUri;

    private final ClientRegistrationRepository clientRegistrationRepository;

    @GetMapping("/main")
    public String main1() {
        return "success";
    }

    @GetMapping("/aaa")
    public OAuth2User index(@AuthenticationPrincipal OAuth2User user) {

        return user;
    }

    //    // 클라이언트가 구현해야하는 코드 - 콜백 URL 에서 인증 코드 처리
    @GetMapping("/callback")
    public String code(@RequestParam String code,
                                   HttpServletResponse servletResponse) {
        // 현재 사용자의 인증 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 없으면(로그인되지 않은 상태) 로그인 페이지로 이동합니다.
        if (authentication != null) {
            authentication.isAuthenticated();
        }// 여기에 로그인 페이지로 리다이렉트하는 코드를 추가할 수 있습니다.
    // 예를 들어, return "redirect:/login";

        // 인증 정보가 있으면(로그인된 상태) 사용자 정보를 확인하거나 다른 작업을 수행할 수 있습니다.
        // 여기에 사용자 정보를 처리하는 코드를 추가할 수 있습니다.

        // 이하 코드는 기존에 작성된 코드를 그대로 사용합니다.
        String credentials = clientId + ":" + clientSecret;
        String encodingCredentials = java.util.Base64.getEncoder().encodeToString(credentials.getBytes());

        AccessTokenRequest request = new AccessTokenRequest(code, "authorization_code", redirectUri);

        HttpResponse<OauthTokenResponse> response;
        try {
            response = Unirest.post("http://localhost:7070/oauth/token")
                    .header("Authorization", "Basic " + encodingCredentials)
                    .fields(request.getMapData())
                    .asObject(OauthTokenResponse.class);

            servletResponse.setHeader("Authorization", "Bearer " + response.getBody().getAccess_token());
        } catch (UnirestException e) {
//            e.printStackTrace();
            return null;
        }

        return response.getBody().getAccess_token();
    }
    @GetMapping("/authorize")
    public void authorize(HttpServletResponse response,
                          @RequestParam("clientRegistrationId") String clientRegistrationId) throws Exception {
        ClientRegistration clientRegistration = this.clientRegistrationRepository.findByRegistrationId(clientRegistrationId);
        OAuth2AuthorizationRequest.Builder builder = OAuth2AuthorizationRequest.authorizationCode();
        builder.authorizationUri(clientRegistration.getProviderDetails().getAuthorizationUri());
        builder.clientId(clientRegistration.getClientId());
        builder.redirectUri(clientRegistration.getRedirectUri());
        builder.scopes(clientRegistration.getScopes());
        OAuth2AuthorizationRequest authorizationRequest = builder.build();

        response.sendRedirect(authorizationRequest.getAuthorizationRequestUri());
    }
}
