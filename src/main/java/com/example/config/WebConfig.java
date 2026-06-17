package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * グローバルCORS設定クラス.
 * /api/** に対してすべてのOrigin・メソッド・ヘッダーを許可する。
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

    /** クライアントからAPIを呼び出すための RestTemplate */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
