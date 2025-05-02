package com.ncu.sportstrainingtracker.service;

import com.ncu.sportstrainingtracker.entity.Athlete;
import com.ncu.sportstrainingtracker.entity.PerformanceRecord;
import com.ncu.sportstrainingtracker.exception.ResourceNotFoundException;
import com.ncu.sportstrainingtracker.repository.AthleteRepository;
import com.ncu.sportstrainingtracker.repository.PerformanceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceRecordService {

    @Autowired
    private PerformanceRecordRepository performanceRecordRepository;

    @Autowired
    private AthleteRepository athleteRepository;

    // Create and save a new performance record
    public PerformanceRecord createPerformanceRecord(Long athleteId, PerformanceRecord record) {
        Athlete athlete = athleteRepository.findById(athleteId)
                .orElseThrow(() -> new ResourceNotFoundException("Athlete not found with id: " + athleteId));

        record.setAthlete(athlete);

        // Check for personal best before saving
        Double previousBest = getPersonalBestValue(athleteId, record.getMetric());

        // Set remark only if it's a PB and no remark was given
        if ((previousBest.isNaN() || record.getValue() > previousBest) &&
                (record.getRemarks() == null || record.getRemarks().isBlank())) {
            record.setRemarks("New Personal Best!");
        }

        return performanceRecordRepository.save(record);
    }

    // Get all performance records for an athlete
    public List<PerformanceRecord> getPerformanceRecordsByAthlete(Long athleteId) {
        return performanceRecordRepository.findByAthleteId(athleteId);
    }

    // Get all records for an athlete filtered by a metric
    public List<PerformanceRecord> getPerformanceRecordsByAthleteAndMetric(Long athleteId, String metric) {
        return performanceRecordRepository.findByAthleteIdAndMetric(athleteId, metric);
    }

    // Get a single performance record by ID
    public PerformanceRecord getPerformanceRecordById(Long id) {
        return performanceRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Performance record not found with id: " + id));
    }

    // Update existing performance record
    public PerformanceRecord updatePerformanceRecord(Long id, PerformanceRecord updatedRecord) {
        PerformanceRecord existing = getPerformanceRecordById(id);

        existing.setMetric(updatedRecord.getMetric());
        existing.setValue(updatedRecord.getValue());
        existing.setRemarks(updatedRecord.getRemarks());
        existing.setDate(updatedRecord.getDate());

        return performanceRecordRepository.save(existing);
    }

    // Delete a performance record by ID
    public void deletePerformanceRecord(Long id) {
        PerformanceRecord existing = getPerformanceRecordById(id);
        performanceRecordRepository.delete(existing);
    }

    // Get top N performers based on metric value
    public List<PerformanceRecord> getTopPerformersByMetric(String metric, int limit) {
        Pageable topN = PageRequest.of(0, limit);
        return performanceRecordRepository.findTopPerformersByMetric(metric, topN);
    }

    // Get highest value (personal best) for a metric by an athlete
    public Double getPersonalBestValue(Long athleteId, String metric) {
        return performanceRecordRepository.findByAthleteIdAndMetric(athleteId, metric)
                .stream()
                .mapToDouble(PerformanceRecord::getValue)
                .max()
                .orElse(Double.NaN);
    }
}
