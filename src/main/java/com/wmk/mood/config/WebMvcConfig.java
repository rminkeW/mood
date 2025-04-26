package com.wmk.mood.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 匹配所有接口（根据实际需求调整路径）
                .allowedOrigins("http://localhost:3000") // 允许你的前端域名+端口（必须与前端实际地址一致，不带哈希）
                .allowCredentials(true) // 允许携带 Cookie 等凭证（如果前端需要发送登录凭证）
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的 HTTP 方法
                .maxAge(3600); // 预检请求（OPTIONS）的缓存时间（秒）
    }
}

