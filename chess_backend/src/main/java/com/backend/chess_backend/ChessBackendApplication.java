package com.backend.chess_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ChessBackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(ChessBackendApplication.class, args);
	}

}
