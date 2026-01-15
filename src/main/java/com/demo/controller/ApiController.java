package com.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Value("${app.version:1.0.0}")
    private String appVersion;

    @Value("${app.environment:development}")
    private String environment;

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("application", "DevOps Demo");
        info.put("version", appVersion);
        info.put("environment", environment);
        info.put("hostname", getHostname());
        info.put("timestamp", Instant.now().toString());
        info.put("uptimeMs", ManagementFactory.getRuntimeMXBean().getUptime());

        Map<String, Object> system = new HashMap<>();
        system.put("javaVersion", System.getProperty("java.version"));
        system.put("osName", System.getProperty("os.name"));
        system.put("osArch", System.getProperty("os.arch"));
        system.put("availableProcessors", Runtime.getRuntime().availableProcessors());
        system.put("totalMemoryMB", Runtime.getRuntime().totalMemory() / 1024 / 1024);
        system.put("freeMemoryMB", Runtime.getRuntime().freeMemory() / 1024 / 1024);
        info.put("system", system);

        return ResponseEntity.ok(info);
    }

    @PostMapping("/echo")
    public ResponseEntity<Map<String, Object>> echo(@RequestBody(required = false) Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        response.put("received", payload != null ? payload : Map.of());
        response.put("timestamp", Instant.now().toString());
        response.put("processedBy", getHostname());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", Instant.now().toString());
        health.put("version", appVersion);
        return ResponseEntity.ok(health);
    }

    private String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "unknown";
        }
    }
}
