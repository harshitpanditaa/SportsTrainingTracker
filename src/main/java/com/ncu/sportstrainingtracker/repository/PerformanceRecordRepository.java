package com.ncu.sportstrainingtracker.repository;

import com.ncu.sportstrainingtracker.entity.PerformanceRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PerformanceRecordRepository extends JpaRepository<PerformanceRecord, Long> {

    List<PerformanceRecord> findByAthleteId(Long athleteId);

    List<PerformanceRecord> findByAthleteIdAndMetric(Long athleteId, String metric);

    @Query("SELECT pr FROM PerformanceRecord pr " +
            "WHERE pr.metric = :metric " +
            "ORDER BY pr.value DESC")
    List<PerformanceRecord> findTopPerformersByMetric(@Param("metric") String metric, Pageable pageable);

    Optional<PerformanceRecord> findTopByAthleteIdAndMetricOrderByValueDesc(Long athleteId, String metric);


}
