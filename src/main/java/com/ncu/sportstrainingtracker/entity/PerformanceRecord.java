package com.ncu.sportstrainingtracker.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.util.Date;

@Schema(description = "Represents a performance record for an athlete")
@Entity
@Table(name = "performance_records")
public class PerformanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the performance record", example = "1")
    private Long id;

    @Schema(description = "The performance metric (e.g., 'Speed', 'Distance', 'Time')", example = "Speed")
    private String metric;

    @Column(name = "metric_value")
    @Schema(description = "The value achieved for the metric", example = "10.5")
    private double value;

    @Schema(description = "Any additional remarks or notes about the performance", example = "Achieved during practice")
    private String remarks;

    @Schema(description = "The date when the performance was recorded", example = "2025-04-30T20:00:00.000+00:00")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "athlete_id")
    @JsonBackReference
    @Schema(description = "The athlete who achieved this performance record")
    private Athlete athlete;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMetric() { return metric; }
    public void setMetric(String metric) { this.metric = metric; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public Athlete getAthlete() { return athlete; }
    public void setAthlete(Athlete athlete) { this.athlete = athlete; }
}