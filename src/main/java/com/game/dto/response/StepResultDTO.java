package com.game.dto.response;

import com.game.domain.PlayerTurnEnum;
import com.game.domain.StepType;
import lombok.Data;

import java.util.Date;

@Data
public class StepResultDTO {
    StepType firstPlayerStepType;
    StepType secondPlayerStepType;
    PlayerTurnEnum winner;
    Date roundDate;
}
