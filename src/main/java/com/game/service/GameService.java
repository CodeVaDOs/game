package com.game.service;

import com.game.domain.*;
import com.game.dto.StepDTO;
import com.game.repository.GameRepository;
import com.game.repository.GameStepRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {
    private final GameRepository repository;
    private final GameStepRepository gameStepRepository;

    public GameService(GameRepository repository, GameStepRepository gameStepRepository) {
        this.repository = repository;
        this.gameStepRepository = gameStepRepository;
    }

    public Game create(Player player) {
        Game game = new Game();
        game.setFirstPlayer(player);
        game.setStatus(GameStatusEnum.NEW);
        return repository.save(game);
    }

    public Game connect(Player player, Long gameId) {
        Game game = repository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
        if (game.getSecondPlayer() != null) throw new RuntimeException("Game already started");
        game.setSecondPlayer(player);
        game.setStatus(GameStatusEnum.IN_PROGRESS);
        return repository.save(game);
    }

    public Game makeStep(Long gameId, StepDTO stepDTO) {
        Game game = repository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
        PlayerTurnEnum playerTurn = stepDTO.getPlayerTurn();
        boolean isFirstPlayer = playerTurn.equals(PlayerTurnEnum.FIRST_PLAYER);

        Optional<GameStep> existedGame = game.getStepList().stream().filter(s -> !s.getStatus().equals(GameStepStatus.COMPLETED)).findFirst();

        GameStep gameStep = existedGame.orElse(new GameStep());


        if (isFirstPlayer) {
            gameStep.setFirstPlayer(game.getFirstPlayer());
            gameStep.setFirstPlayerStepType(stepDTO.getStepType());
            if (gameStep.getSecondPlayerStepType() == null) gameStep.setStatus(GameStepStatus.WAITING_FOR_SECOND_PLAYER);
        } else {
            gameStep.setSecondPlayer(game.getSecondPlayer());
            gameStep.setSecondPlayerStepType(stepDTO.getStepType());
            if (gameStep.getFirstPlayerStepType() == null) gameStep.setStatus(GameStepStatus.WAITING_FOR_FIRST_PLAYER);
        }

        if (gameStep.getFirstPlayerStepType() != null && gameStep.getSecondPlayerStepType() != null) {
            gameStep.setStatus(GameStepStatus.COMPLETED);
        }

        if (existedGame.isEmpty()) {
            game.getStepList().add(gameStep);
        }

        gameStepRepository.save(gameStep);
        return repository.save(game);
    }
}
