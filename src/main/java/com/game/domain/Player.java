package com.game.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "players")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player extends BaseEntity {
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    List<Game> gameList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    List<GameStep> stepList = new ArrayList<>();

    String name;
}
