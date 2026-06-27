package com.example.simpleorder.intercepter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    private final String TOKEN_HEADER = "token";
    private final String AUTHORIZED_TOKEN = "bingo";    // 放行token

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(TOKEN_HEADER);
        if (!AUTHORIZED_TOKEN.equals(token)) {
            log.warn("请求token错误");
            return false;
        }
        return true;
    }
}
