package com.backend.chess_backend.repository;

import com.backend.chess_backend.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    Game findByGameID(String gameId); // Updated method name
    Game findByUserId(String userId); // Assuming a relationship between Game and User entities
}
