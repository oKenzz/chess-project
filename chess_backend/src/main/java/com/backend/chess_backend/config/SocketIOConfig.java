package com.backend.chess_backend.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class SocketIOConfig {

    @Value("${socket-server.host}")
    private String host;

    @Value("${socket-server.port}")
    private Integer port;

    @Bean
    public SocketIOServer socketIOServer() {
        // Set config
        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);

        // Configure CORS
        config.setOrigin(":*:");

        // Log URL to connect to
        System.out.println("\nURL to connect: http://" + host + ":" + port + "\n");

        return new SocketIOServer(config);
    }
}