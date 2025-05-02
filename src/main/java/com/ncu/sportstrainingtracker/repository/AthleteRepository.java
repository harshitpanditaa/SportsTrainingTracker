package com.ncu.sportstrainingtracker.repository;

import com.ncu.sportstrainingtracker.entity.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {
}
