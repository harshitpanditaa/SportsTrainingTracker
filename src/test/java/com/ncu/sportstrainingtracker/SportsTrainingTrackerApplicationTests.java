package com.ncu.sportstrainingtracker;

import com.ncu.sportstrainingtracker.entity.Athlete;
import com.ncu.sportstrainingtracker.entity.PerformanceRecord;
import com.ncu.sportstrainingtracker.repository.AthleteRepository;
import com.ncu.sportstrainingtracker.service.PerformanceRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class SportsTrainingTrackerApplicationTests {

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private PerformanceRecordService performanceRecordService;

    private Athlete testAthlete;

    @BeforeEach
    void setUp() {
        athleteRepository.deleteAll(); // Clean slate
        testAthlete = athleteRepository.save(new Athlete("Test Runner", "Test Team", 25));
    }

    @Test
    void testAddPerformanceRecord() {
        PerformanceRecord record = new PerformanceRecord();
        record.setMetric("100m Sprint");
        record.setValue(10.5);
        record.setRemarks("Initial Test");
        record.setDate(new Date());

        PerformanceRecord saved = performanceRecordService.createPerformanceRecord(testAthlete.getId(), record);

        assertNotNull(saved.getId());
        assertEquals("100m Sprint", saved.getMetric());
        assertEquals(10.5, saved.getValue());
        assertEquals("Initial Test", saved.getRemarks());
    }

    @Test
    void testPersonalBestUpdate() {
        // Add first record
        PerformanceRecord first = new PerformanceRecord();
        first.setMetric("Long Jump");
        first.setValue(5.8);
        first.setDate(new Date());
        performanceRecordService.createPerformanceRecord(testAthlete.getId(), first);

        // Add better record
        PerformanceRecord second = new PerformanceRecord();
        second.setMetric("Long Jump");
        second.setValue(6.3);
        second.setDate(new Date());
        PerformanceRecord savedSecond = performanceRecordService.createPerformanceRecord(testAthlete.getId(), second);

        // Assert the latest record has "New Personal Best!" remark
        assertEquals("New Personal Best!", savedSecond.getRemarks());

        // Also check if it's really the highest value
        List<PerformanceRecord> records = performanceRecordService.getPerformanceRecordsByAthleteAndMetric(
                testAthlete.getId(), "Long Jump"
        );
        double best = records.stream().mapToDouble(PerformanceRecord::getValue).max().orElse(0);
        assertEquals(6.3, best);
    }
}
