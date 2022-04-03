package com.game.controller;

import com.game.domain.Game;
import com.game.dto.PlayerDTO;
import com.game.dto.StepDTO;
import com.game.dto.response.GameDTO;
import com.game.service.GameService;
import com.game.service.PlayerService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins="*")
public class GameController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GameService gameService;
    private final PlayerService playerService;

    public GameController(SimpMessagingTemplate simpMessagingTemplate, GameService gameService, PlayerService playerService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @PostMapping("/create")
    public GameDTO createGame(@RequestBody PlayerDTO playerDTO) {
        Game game = gameService.create(playerService.createIfNotExist(playerDTO.getName()));
        GameDTO gameDTO = GameDTO.fromEntity(game);
        return gameDTO;
    }

    @PostMapping("/connect/{gameId}")
    public GameDTO connectToGame(@RequestBody PlayerDTO playerDTO, @PathVariable Long gameId) {
        Game game = gameService.connect(playerService.createIfNotExist(playerDTO.getName()), gameId);
        GameDTO gameDTO = GameDTO.fromEntity(game);
        simpMessagingTemplate.convertAndSend("/topic/game-progress/" + gameDTO.getId(), gameDTO);
        return gameDTO;
    }

    @PostMapping("/step/{gameId}")
    public GameDTO makeStep(@RequestBody StepDTO stepDTO, @PathVariable Long gameId) {
        Game game = gameService.makeStep(gameId, stepDTO);
        GameDTO gameDTO = GameDTO.fromEntity(game);
        simpMessagingTemplate.convertAndSend("/topic/game-progress/" + gameDTO.getId(), gameDTO);
        return gameDTO;
    }
}
