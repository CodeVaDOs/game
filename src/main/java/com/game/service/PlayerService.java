package com.game.service;

import com.game.domain.Player;
import com.game.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public Player createIfNotExist(String name) {
        Optional<Player> playerByName = repository.findPlayerByName(name);
        if (playerByName.isPresent()) return playerByName.get();

        Player player = new Player();
        player.setName(name);
        return repository.save(player);
    }


}
