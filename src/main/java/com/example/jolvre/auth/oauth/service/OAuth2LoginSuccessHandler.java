package com.example.jolvre.auth.oauth.service;

import com.example.jolvre.auth.jwt.service.JwtService;
import com.example.jolvre.auth.oauth.entity.CustomOAuth2User;
import com.example.jolvre.user.entity.Role;
import com.example.jolvre.user.entity.User;
import com.example.jolvre.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;
    private final UserRepository userRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("[AUTH] : OAuth2 Login 성공!");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            // User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
            if (oAuth2User.getRole() == Role.GUEST) {
                log.info("[AUTH] : 추가 회원가입 폼 진입");
                log.info("------- {}", oAuth2User.getUser());
                String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
                response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);

                jwtService.sendAccessAndRefreshToken(response, accessToken, null);

                response.sendRedirect(generateUriSignUp(accessToken)); // 프론트의 회원가입 추가 정보 입력 폼으로 리다이렉트
//                response.sendRedirect(generateUriSignUp1(oAuth2User.getUser()));

            } else {
                loginSuccess(response, oAuth2User); // 로그인에 성공한 경우 access, refresh 토큰 생성
            }
        } catch (Exception e) {
            throw e;
        }

    }

    // TODO : 소셜 로그인 시에도 무조건 토큰 생성하지 말고 JWT 인증 필터처럼 RefreshToken 유/무에 따라 다르게 처리해보기
    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
        String refreshToken = jwtService.createRefreshToken();

        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);

        response.sendRedirect(generateUriLogin(accessToken, refreshToken)); // 홈 페이지 uri + 어세스 토큰 넘겨주는 기능 추가하기
    }


    private String generateUriSignUp(String accessToken) {
        StringBuilder sb = new StringBuilder();
        String baseUrl = "/test2"; // 추가 회원가입 입력폼 uri

        StringBuilder uri = sb.append(baseUrl).append("?").append("accessToken=").append(accessToken);

        return uri.toString();
    }

    private String generateUriSignUp1(User user) {
        StringBuilder sb = new StringBuilder();
        String baseUrl = "/api/v1/auth/oauth/signUp"; // 추가 회원가입 입력폼 uri

        StringBuilder uri = sb.append(baseUrl).append("?")
                .append("imageUrl").append(user.getImageUrl()).append("&")
                .append("nickname").append(user.getNickname()).append("&")
                .append("socialId").append(user.getSocialId()).append("&")
                .append("socialType").append(user.getSocialType());

        return uri.toString();
    }

    private String generateUriLogin(String accessToken, String refreshToken) {
        StringBuilder sb = new StringBuilder();
        String baseUrl = "/api/v1/auth/login"; // 추가 회원가입 입력폼 uri

        StringBuilder uri = sb.append(baseUrl).append("?").append("accessToken=").append(accessToken)
                .append("&").append("refreshToken=").append(refreshToken);

        return uri.toString();
    }

}
