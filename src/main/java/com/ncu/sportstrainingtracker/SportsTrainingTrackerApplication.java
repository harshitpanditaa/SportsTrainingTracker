package com.ncu.sportstrainingtracker;

import com.ncu.sportstrainingtracker.entity.Athlete;
import com.ncu.sportstrainingtracker.repository.AthleteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class SportsTrainingTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportsTrainingTrackerApplication.class, args);
        log.info("Sports Training Tracker Application Started Successfully!");
    }

    @Bean
    public CommandLineRunner runner(AthleteRepository athleteRepository) {
        return args -> {
            if (athleteRepository.count() == 0) {
                athleteRepository.save(new Athlete("Usain Bolt", "Team Jamaica", 34));
                athleteRepository.save(new Athlete("Carl Lewis", "Team USA", 35));
                athleteRepository.save(new Athlete("Noah Lyles", "Team USA", 27));
                log.info("Sample athletes added to the database.");
            } else {
                log.info("Athletes already exist. Skipping sample data insertion.");
            }
        };
    }

}
