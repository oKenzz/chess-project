package com.backend.chess_backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.backend.chess_backend.socket.GameManager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Controller
public class AdminAPI {
    private final GameManager gameManager;

    @Autowired
    public AdminAPI(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Value("${ADMIN_PIN}")
    private String adminPin;

    @GetMapping("/admin")
    public String admin(@RequestParam(name = "pin", required = false) String pin, Model model) {
        if (pin == null || !pin.equals(adminPin)) {
            return "Unauthorized";
        }
        List<Map<String, Object>> gameInfo = gameManager.getGamesInfo();
        model.addAttribute("gameInfo", gameInfo);
        return "AdminPage";
    }

    @PostMapping("/admin/kick")
    public ResponseEntity<?> kickPlayer(@RequestBody Map<String, Object> payload) {

        String pin = (String) payload.get("pin");
        String uuid = (String) payload.get("uuid");
        if (pin == null || !pin.equals(adminPin)) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        ;

        gameManager.kickPlayer(uuid);
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("status", "success");
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/reset")
    public ResponseEntity<?> resetGame(@RequestBody Map<String, Object> payload) {

        String pin = (String) payload.get("pin");
        if (pin == null || !pin.equals(adminPin)) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        ;
        gameManager.reset();
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("status", "success");
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

}
