package com.dawnlight.chronicle_dawnlight.common.utils.config;

import com.dawnlight.chronicle_dawnlight.common.utils.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器，并指定拦截路径和排除路径
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**") // 拦截所有路径
                .excludePathPatterns(
                        "/login", // 登录接口
                        "/register", // 注册接口
                        "/getEmail",// 获取邮箱验证码接口,
                        "/api"//爬虫接口
                );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://www.alba.us.kg","http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);
    }
}
