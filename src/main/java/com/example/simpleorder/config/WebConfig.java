package com.example.simpleorder.config;

import com.example.simpleorder.controller.OrderController;
import com.example.simpleorder.intercepter.TokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final TokenInterceptor tokenInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有，如果需要可以对用于测试的路由放行
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**");
    }
}
