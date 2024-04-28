package com.example.jolvre.auth.exception;

import com.example.jolvre.common.error.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.error("[AUTH] : 가입되지 않은 사용자 접근 {}", authException.getMessage());
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(
                ErrorResponse.of(HttpStatus.UNAUTHORIZED, authException.getMessage(), request.getRequestURI()
                ).convertToJson()
        );
//        response.sendRedirect("/api/v1/auth/test"); // 로그인 폼으로 리다이렉트
    }
}
