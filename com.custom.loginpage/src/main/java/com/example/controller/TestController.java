package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class TestController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 로그인 처리 (여기서는 간단히 출력)
        System.out.println("username: " + username);

        // 로그인 처리 (여기서는 간단히 username이 "admin"이고 password가 "password"인 경우를 성공으로 가정)
        if ("user".equals(username) && "1234".equals(password)) {
            // 세션에 사용자 이름 저장
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // 홈 페이지로 리다이렉트
            return "redirect:/home";
        } else {
            // 로그인 실패 시 에러 페이지로 리다이렉트
            return "redirect:/login?error";
        }
    }

}
