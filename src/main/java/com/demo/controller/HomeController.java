package com.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller
public class HomeController {

    @Value("${app.version:1.0.0}")
    private String appVersion;

    @Value("${app.environment:development}")
    private String environment;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("hostname", getHostname());
        model.addAttribute("version", appVersion);
        model.addAttribute("environment", environment);
        model.addAttribute("deployTime", getFormattedTime());
        model.addAttribute("uptime", getUptime());
        model.addAttribute("javaVersion", System.getProperty("java.version"));
        model.addAttribute("osName", System.getProperty("os.name"));
        return "index";
    }

    private String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "unknown";
        }
    }

    private String getFormattedTime() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z")
                .withZone(ZoneId.systemDefault())
                .format(Instant.now());
    }

    private String getUptime() {
        long uptimeMs = ManagementFactory.getRuntimeMXBean().getUptime();
        long seconds = uptimeMs / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) {
            return String.format("%dd %dh %dm", days, hours % 24, minutes % 60);
        } else if (hours > 0) {
            return String.format("%dh %dm %ds", hours, minutes % 60, seconds % 60);
        } else if (minutes > 0) {
            return String.format("%dm %ds", minutes, seconds % 60);
        } else {
            return String.format("%ds", seconds);
        }
    }
}
