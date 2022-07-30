package com.revined.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public Object test() {
        return Map.of("success", Boolean.TRUE, "datetime", LocalDateTime.now());
    }
}
