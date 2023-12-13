package com.backend.chess_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.backend.chess_backend.socket.GameManager;

@RestController
@RequestMapping("api/v1")
@Component
public class GameAPI {

    private final GameManager gameManager;

    @Autowired
    public GameAPI(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @GetMapping("/getRooms")
    public List<String> getRooms() {
        return gameManager.getGames();
    }

    @GetMapping("/isRoomJoinable")
    public boolean isRoomJoinable(@RequestParam String roomId) {
        return gameManager.roomExist(roomId);
    }

}
