package com.backend.chess_backend.data;

import lombok.Data;

@Data
public class Message {

    private String senderName;
    private String targetUserName;
    private String message;

}