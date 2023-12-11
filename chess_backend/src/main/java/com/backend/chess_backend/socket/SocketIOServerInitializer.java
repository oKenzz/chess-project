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
    private final ConnectionManager connectionManager; // Add ConnectionManager as a dependency

    @Autowired
    public SocketIOServerInitializer(SocketIOServer server, ChessHandler chessHandler,
            ConnectionManager connectionManager) {
        this.server = server;
        this.chessHandler = chessHandler;
        this.connectionManager = connectionManager; // Initialize ConnectionManager
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startServer() {
        server.addConnectListener(connectionManager.onConnected());
        server.addDisconnectListener(connectionManager.onDisconnected());
        server.addEventListener("message", String.class, chessHandler::onChatMessage);
        server.addEventListener("getChatMessages", Void.class, chessHandler::getChatMessagesListener);
        server.addEventListener("move", String.class, chessHandler::moveListener);
        server.addEventListener("getGameState", Void.class, chessHandler::getGameStateListener);
        server.addEventListener("getPossibleMoves", String.class, chessHandler::possibleMoveListener);
        server.addEventListener("computerMove", Void.class, chessHandler::computerMoveListener);
        server.addEventListener("restart", Void.class, chessHandler::restartBoardListener);
        server.addEventListener("surrender", Void.class, chessHandler::surrenderListener);
        server.addEventListener("undo", Void.class, chessHandler::undoMoveListener);
        server.start();
    }

    @PreDestroy
    public void stopServer() {
        server.stop();
    }
}
