package com.game.domain;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "steps")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameStep extends BaseEntity {
    @Enumerated(value = EnumType.STRING)
    StepType firstPlayerStepType;

    @Enumerated(value = EnumType.STRING)
    StepType secondPlayerStepType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "first_player_id", referencedColumnName = "id")
    Player firstPlayer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "second_player_id", referencedColumnName = "id")
    Player secondPlayer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    Game game;

    @Enumerated(value = EnumType.STRING)
    GameStepStatus status;
}
