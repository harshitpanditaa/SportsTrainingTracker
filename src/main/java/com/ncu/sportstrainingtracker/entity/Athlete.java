package com.ncu.sportstrainingtracker.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Represents an athlete")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "athletes")
public class Athlete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the athlete", example = "1")
    private Long id;

    @Schema(description = "Name of the athlete", example = "John Doe")
    private String name;

    @Schema(description = "Age of the athlete", example = "25")
    private int age;

    @Schema(description = "Team the athlete belongs to", example = "Vikings")
    private String team;

    @OneToMany(mappedBy = "athlete", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Schema(description = "List of performance records associated with the athlete")
    private List<PerformanceRecord> performanceRecords;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getTeam() { return team; }
    public void setTeam(String team) { this.team = team; }

    public List<PerformanceRecord> getPerformanceRecords() { return performanceRecords; }
    public void setPerformanceRecords(List<PerformanceRecord> performanceRecords) { this.performanceRecords = performanceRecords; }

    public Athlete(String name, String team, int age) {
        this.name = name;
        this.team = team;
        this.age = age;
    }

}