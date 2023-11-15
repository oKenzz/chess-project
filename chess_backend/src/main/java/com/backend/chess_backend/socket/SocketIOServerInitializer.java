package com.backend.chess_backend.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PreDestroy;

@Component
public class SocketIOServerInitializer {

    private final SocketIOServer server;
    private final TestHandler testHandler;

    @Autowired
    public SocketIOServerInitializer(SocketIOServer server, TestHandler testHandler) {
        this.server = server;
        this.testHandler = testHandler;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startServer() {
        server.addConnectListener(testHandler.onConnected());
        server.addDisconnectListener(testHandler.onDisconnected());
        server.addEventListener("message", String.class, testHandler::onChatMessage);
        server.start();
    }

    @PreDestroy
    public void stopServer() {
        server.stop();
    }
}
