package com.game.dto.response;

import com.game.domain.*;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GameDTO {
    private Long id;

    private GameStatusEnum status;

    private int firstPlayerScore;
    private int secondPlayerScore;

    private GameStepStatus activeGameStepStatus;
    private String firstPlayerName;
    private String secondPlayerName;

    private Integer roundNumber;

    private List<StepResultDTO> stepList;

    public static GameDTO fromEntity(Game game) {
        GameDTO gameDTO = new GameDTO();
        gameDTO.id = game.getId();
        gameDTO.status = game.getStatus();
        gameDTO.firstPlayerScore = game.getScoreByPlayerTurn(PlayerTurnEnum.FIRST_PLAYER);
        gameDTO.secondPlayerScore = game.getScoreByPlayerTurn(PlayerTurnEnum.SECOND_PLAYER);
        GameStep activeGameStep = game.getStepList().stream().filter(s -> !s.getStatus().equals(GameStepStatus.COMPLETED)).findFirst().orElse(null);
        if (activeGameStep != null) gameDTO.activeGameStepStatus = activeGameStep.getStatus();
        else gameDTO.activeGameStepStatus = null;

        gameDTO.roundNumber = game.getStepList().size();

        gameDTO.firstPlayerName = game.getFirstPlayer().getName();
        if (game.getSecondPlayer() != null) gameDTO.secondPlayerName = game.getSecondPlayer().getName();

        if (game.getStepList().stream().anyMatch(s -> s.getStatus().equals(GameStepStatus.COMPLETED))) {
            gameDTO.stepList = game.getStepList().stream().filter(gs -> gs.getFirstPlayerStepType() != null && gs.getSecondPlayerStepType() != null).map((s) -> {
                StepResultDTO stepResultDTO = new StepResultDTO();
                stepResultDTO.firstPlayerStepType = s.getFirstPlayerStepType();
                stepResultDTO.secondPlayerStepType = s.getSecondPlayerStepType();
                stepResultDTO.winner = game.isPlayerWon(s.getFirstPlayerStepType(), s.getSecondPlayerStepType()) ? PlayerTurnEnum.FIRST_PLAYER : PlayerTurnEnum.SECOND_PLAYER;
                stepResultDTO.roundDate = s.getCreatedDate();
                return stepResultDTO;
            }).collect(Collectors.toList());
        }

        return gameDTO;
    }
}
