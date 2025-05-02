package com.ncu.sportstrainingtracker.service;

import com.ncu.sportstrainingtracker.entity.Athlete;
import com.ncu.sportstrainingtracker.exception.ResourceNotFoundException;
import com.ncu.sportstrainingtracker.repository.AthleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AthleteService {

    @Autowired
    private AthleteRepository athleteRepository;

    public Athlete createAthlete(Athlete athlete) {
        return athleteRepository.save(athlete);
    }

    public List<Athlete> getAllAthletes() {
        return athleteRepository.findAll();
    }

    public Athlete getAthleteById(Long id) {
        return athleteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Athlete not found with id: " + id));
    }

    public Athlete updateAthlete(Long id, Athlete updatedAthlete) {
        Athlete existing = getAthleteById(id);
        existing.setName(updatedAthlete.getName());
        existing.setTeam(updatedAthlete.getTeam());
        existing.setAge(updatedAthlete.getAge());
        return athleteRepository.save(existing);
    }

    public void deleteAthlete(Long id) {
        Athlete existing = getAthleteById(id);
        athleteRepository.delete(existing);
    }
}
