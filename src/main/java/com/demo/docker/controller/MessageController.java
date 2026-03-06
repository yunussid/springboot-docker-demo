package com.demo.docker.controller;

import com.demo.docker.model.Message;
import com.demo.docker.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // Welcome endpoint
    @GetMapping("/")
    public ResponseEntity<Message> welcome() {
        return ResponseEntity.ok(messageService.getWelcomeMessage());
    }

    // Hello endpoint
    @GetMapping("/hello")
    public ResponseEntity<Message> hello() {
        return ResponseEntity.ok(messageService.getCustomMessage("Hello from Docker Container!"));
    }

    // Custom message endpoint
    @GetMapping("/message/{text}")
    public ResponseEntity<Message> customMessage(@PathVariable String text) {
        return ResponseEntity.ok(messageService.getCustomMessage(text));
    }

    // Health check endpoint
    @GetMapping("/health")
    public ResponseEntity<Message> health() {
        return ResponseEntity.ok(messageService.getHealthStatus());
    }

    // Info endpoint - shows container/app info
    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> info() {
        Map<String, String> info = Map.of(
                "application", "springboot-docker-demo",
                "version", "1.0.0",
                "java.version", System.getProperty("java.version"),
                "os.name", System.getProperty("os.name"),
                "os.arch", System.getProperty("os.arch")
        );
        return ResponseEntity.ok(info);
    }

    // Echo endpoint - POST
    @PostMapping("/echo")
    public ResponseEntity<Message> echo(@RequestBody Map<String, String> body) {
        String text = body.getOrDefault("message", "No message provided");
        return ResponseEntity.ok(messageService.getCustomMessage("Echo: " + text));
    }
}
