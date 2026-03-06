package com.demo.docker.service;

import com.demo.docker.model.Message;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class MessageService {

    public Message getWelcomeMessage() {
        return Message.builder()
                .id(UUID.randomUUID().toString())
                .content("Welcome to Spring Boot Docker Demo!")
                .timestamp(getCurrentTimestamp())
                .hostname(getHostname())
                .build();
    }

    public Message getCustomMessage(String text) {
        return Message.builder()
                .id(UUID.randomUUID().toString())
                .content(text)
                .timestamp(getCurrentTimestamp())
                .hostname(getHostname())
                .build();
    }

    public Message getHealthStatus() {
        return Message.builder()
                .id(UUID.randomUUID().toString())
                .content("Application is running healthy!")
                .timestamp(getCurrentTimestamp())
                .hostname(getHostname())
                .build();
    }

    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unknown";
        }
    }
}
