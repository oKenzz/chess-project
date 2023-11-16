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
    private final ChessHandler chessHandler;

    @Autowired
    public SocketIOServerInitializer(SocketIOServer server, ChessHandler chessHandler) {
        this.server = server;
        this.chessHandler = chessHandler;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startServer() {
        server.addConnectListener(chessHandler.onConnected());
        server.addDisconnectListener(chessHandler.onDisconnected());
        server.addEventListener("message", String.class, chessHandler::onChatMessage);
        server.addEventListener("move", String.class, chessHandler::moveListener);
        server.addEventListener("newGamePosition", String.class, chessHandler::newGamePostionListener);

        server.start();
    }

    @PreDestroy
    public void stopServer() {
        server.stop();
    }
}
