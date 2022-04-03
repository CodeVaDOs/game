package com.game.dto;

import com.game.domain.PlayerTurnEnum;
import com.game.domain.StepType;
import lombok.Data;

@Data
public class StepDTO {
    StepType stepType;
    PlayerTurnEnum playerTurn;
}
