package com.game.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "games")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "first_player_id", referencedColumnName = "id")
    Player firstPlayer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "second_player_id", referencedColumnName = "id")
    Player secondPlayer;

    @OneToMany(fetch = FetchType.LAZY)
    List<GameStep> stepList = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    GameStatusEnum status;

    public int getScoreByPlayerTurn(PlayerTurnEnum playerTurnEnum) {
        return (int) stepList.stream()
                .filter(s -> {
                    if (s.firstPlayerStepType == null || s.secondPlayerStepType == null) return false;
                    boolean isFirstPlayer = playerTurnEnum.equals(PlayerTurnEnum.FIRST_PLAYER);
                    StepType turnedPlayer = isFirstPlayer ? s.firstPlayerStepType : s.secondPlayerStepType;
                    StepType otherPlayer = isFirstPlayer ? s.secondPlayerStepType : s.firstPlayerStepType;
                    return isPlayerWon(turnedPlayer, otherPlayer);
                }).count();
    }

    public Boolean isPlayerWon(StepType firstPlayerStep, StepType secondPlayerStep) {
        if (firstPlayerStep.equals(secondPlayerStep)) return false;
        if (firstPlayerStep.equals(StepType.SCISSORS) && secondPlayerStep.equals(StepType.PAPER)) return true;
        if (firstPlayerStep.equals(StepType.PAPER) && secondPlayerStep.equals(StepType.STONE)) return true;
        return firstPlayerStep.equals(StepType.STONE) && secondPlayerStep.equals(StepType.SCISSORS);
    }
}
