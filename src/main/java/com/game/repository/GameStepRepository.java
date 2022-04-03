package com.game.repository;

import com.game.domain.GameStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameStepRepository extends JpaRepository<GameStep, Long> {
}
