package com.ncu.sportstrainingtracker.controller;

import com.ncu.sportstrainingtracker.entity.Athlete;
import com.ncu.sportstrainingtracker.service.AthleteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/athletes")
@Tag(name = "Athletes", description = "Operations related to athletes")
public class AthleteController {

    @Autowired
    private AthleteService athleteService;

    @Operation(summary = "Add a new athlete")
    @ApiResponse(responseCode = "201", description = "Athlete created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Athlete.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PreAuthorize("hasAuthority('COACH')")
    @PostMapping
    public ResponseEntity<Athlete> addAthlete(@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Athlete object to be added",
            required = true,
            content = @Content(schema = @Schema(implementation = Athlete.class))) Athlete athlete) {
        return ResponseEntity.status(HttpStatus.CREATED).body(athleteService.createAthlete(athlete));
    }

    @Operation(summary = "Update an existing athlete by ID")
    @ApiResponse(responseCode = "200", description = "Athlete updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Athlete.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Athlete not found")
    @PreAuthorize("hasAuthority('COACH')")
    @PutMapping("/{id}")
    public ResponseEntity<Athlete> updateAthlete(@PathVariable Long id, @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Updated athlete object",
            required = true,
            content = @Content(schema = @Schema(implementation = Athlete.class))) Athlete updatedAthlete) {
        return ResponseEntity.ok(athleteService.updateAthlete(id, updatedAthlete));
    }

    @Operation(summary = "Get all athletes")
    @ApiResponse(responseCode = "200", description = "Successful retrieval",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(type = "array", implementation = Athlete.class)))
    @PreAuthorize("hasAnyAuthority('ANALYST', 'COACH')")
    @GetMapping
    public ResponseEntity<List<Athlete>> getAllAthletes() {
        return ResponseEntity.ok(athleteService.getAllAthletes());
    }

    @Operation(summary = "Get an athlete by ID")
    @ApiResponse(responseCode = "200", description = "Successful retrieval",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Athlete.class)))
    @ApiResponse(responseCode = "404", description = "Athlete not found")
    @PreAuthorize("hasAnyAuthority('ANALYST', 'COACH')")
    @GetMapping("/{id}")
    public ResponseEntity<Athlete> getAthleteById(@PathVariable Long id) {
        return ResponseEntity.ok(athleteService.getAthleteById(id));
    }

    @Operation(summary = "Delete an athlete by ID")
    @ApiResponse(responseCode = "204", description = "Athlete deleted successfully")
    @ApiResponse(responseCode = "404", description = "Athlete not found")
    @PreAuthorize("hasAuthority('COACH')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAthlete(@PathVariable Long id) {
        athleteService.deleteAthlete(id);
        return ResponseEntity.noContent().build();
    }
}
