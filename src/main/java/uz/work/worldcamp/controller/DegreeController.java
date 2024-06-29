package uz.work.worldcamp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.work.worldcamp.dtos.createDto.DegreeCreateDTO;
import uz.work.worldcamp.dtos.responceDto.DegreeResponseDTO;
import uz.work.worldcamp.service.degreeService.DegreeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/degrees")
public class DegreeController {
    private final DegreeService degreeService;

    @Operation(
            description = "Create a new degree",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<DegreeResponseDTO> createDegree(@RequestBody DegreeCreateDTO degreeCreateDTO) {
        DegreeResponseDTO createdDegree = degreeService.createDegree(degreeCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDegree);
    }

    @Operation(
            description = "Get all degrees",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PermitAll
    @GetMapping("/getALL")
    public ResponseEntity<List<DegreeResponseDTO>> getAllDegrees() {
        List<DegreeResponseDTO> degrees = degreeService.getAllDegrees();
        return ResponseEntity.ok(degrees);
    }

    @Operation(
            description = "Update a degree by ID",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<DegreeResponseDTO> updateDegree(@PathVariable UUID id, @RequestBody DegreeCreateDTO degreeCreateDTO) {
        DegreeResponseDTO updatedDegree = degreeService.update(id, degreeCreateDTO);
        return ResponseEntity.ok(updatedDegree);
    }

    @Operation(
            description = "Get a degree by ID",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<DegreeResponseDTO> getDegreeById(@PathVariable UUID id) {
        DegreeResponseDTO degree = degreeService.getById(id);
        return ResponseEntity.ok(degree);
    }

    @Operation(
            description = "Delete a degree by ID",
            method = "DELETE method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDegree(@PathVariable UUID id) {
        String message = degreeService.delete(id);
        return ResponseEntity.ok(message);
    }

    @Operation(
            description = "Activate a degree by ID",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/activate/{id}")
    public ResponseEntity<String> activateDegree(@PathVariable UUID id) {
        String message = degreeService.activateDegree(id);
        return ResponseEntity.ok(message);
    }
}

