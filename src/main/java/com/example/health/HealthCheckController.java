package com.example.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Renderスリープ回避用のヘルスチェックコントローラー.
 */
@RestController
@RequestMapping("/api")
public class HealthCheckController {

    @GetMapping("/health")
    public String health() {
        return "OK";
    }
}