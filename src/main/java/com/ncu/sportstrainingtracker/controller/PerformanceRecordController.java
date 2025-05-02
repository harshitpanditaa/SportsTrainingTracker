package com.ncu.sportstrainingtracker.controller;

import com.ncu.sportstrainingtracker.entity.PerformanceRecord;
import com.ncu.sportstrainingtracker.service.PerformanceRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performance-records")
@Tag(name = "Performance Records", description = "Operations related to athlete performance records")
public class PerformanceRecordController {

    @Autowired
    private PerformanceRecordService performanceRecordService;

    @Operation(summary = "Add a new performance record for a specific athlete")
    @ApiResponse(responseCode = "201", description = "Performance record created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PerformanceRecord.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Athlete not found")
    @PostMapping("/athlete/{athleteId}")
    public ResponseEntity<PerformanceRecord> addPerformanceRecord(
            @Parameter(description = "ID of the athlete", required = true) @PathVariable Long athleteId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Performance record object to be added",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PerformanceRecord.class)))
            @RequestBody PerformanceRecord performanceRecord) {
        return ResponseEntity.status(HttpStatus.CREATED).body(performanceRecordService.createPerformanceRecord(athleteId, performanceRecord));
    }

    @Operation(summary = "Update an existing performance record by ID")
    @ApiResponse(responseCode = "200", description = "Performance record updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = PerformanceRecord.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Performance record not found")
    @PutMapping("/{recordId}")
    public ResponseEntity<PerformanceRecord> updatePerformanceRecord(
            @Parameter(description = "ID of the performance record to update", required = true) @PathVariable Long recordId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated performance record object",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PerformanceRecord.class)))
            @RequestBody PerformanceRecord updatedRecord) {
        return ResponseEntity.ok(performanceRecordService.updatePerformanceRecord(recordId, updatedRecord));
    }

    @Operation(summary = "Delete a performance record by ID")
    @ApiResponse(responseCode = "204", description = "Performance record deleted successfully")
    @ApiResponse(responseCode = "404", description = "Performance record not found")
    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deletePerformanceRecord(
            @Parameter(description = "ID of the performance record to delete", required = true) @PathVariable Long recordId) {
        performanceRecordService.deletePerformanceRecord(recordId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all performance records for a specific athlete")
    @ApiResponse(responseCode = "200", description = "Successful retrieval",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = PerformanceRecord.class)))
    @ApiResponse(responseCode = "404", description = "Athlete not found")
    @GetMapping("/athlete/{athleteId}")
    public ResponseEntity<List<PerformanceRecord>> getPerformanceRecordsByAthlete(
            @Parameter(description = "ID of the athlete", required = true) @PathVariable Long athleteId) {
        return ResponseEntity.ok(performanceRecordService.getPerformanceRecordsByAthlete(athleteId));
    }

    @Operation(summary = "Get performance records for a specific athlete and metric")
    @ApiResponse(responseCode = "200", description = "Successful retrieval",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = PerformanceRecord.class)))
    @ApiResponse(responseCode = "404", description = "Athlete not found")
    @GetMapping("/athlete/{athleteId}/metric/{metric}")
    public ResponseEntity<List<PerformanceRecord>> getPerformanceRecordsByAthleteAndMetric(
            @Parameter(description = "ID of the athlete", required = true) @PathVariable Long athleteId,
            @Parameter(description = "Performance metric (e.g., 'Speed', 'Distance')", required = true) @PathVariable String metric) {
        return ResponseEntity.ok(performanceRecordService.getPerformanceRecordsByAthleteAndMetric(athleteId, metric));
    }

    @Operation(summary = "Get the leaderboard for a specific performance metric")
    @ApiResponse(responseCode = "200", description = "Successful retrieval",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = PerformanceRecord.class)))
    @GetMapping("/leaderboard/{metric}")
    public ResponseEntity<List<PerformanceRecord>> getLeaderboard(
            @Parameter(description = "Performance metric to get the leaderboard for", required = true) @PathVariable String metric,
            @Parameter(description = "Maximum number of top performers to retrieve", example = "5") @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(performanceRecordService.getTopPerformersByMetric(metric, limit));
    }
}