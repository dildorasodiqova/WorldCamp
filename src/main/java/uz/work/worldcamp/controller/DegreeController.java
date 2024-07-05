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
import java.util.Locale;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/degrees")
public class DegreeController {
    private final DegreeService degreeService;

    @Operation(
            description = "Create a new degree",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<DegreeResponseDTO> createDegree(@RequestBody DegreeCreateDTO degree, Locale locale) {
        DegreeResponseDTO createdDegree = degreeService.createDegree(degree, locale);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDegree);
    }

    @Operation(
            description = "Get all degrees",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PermitAll
    @GetMapping("/getALL/{universityId}")
    public ResponseEntity<List<DegreeResponseDTO>> getAllDegrees(
            @PathVariable UUID universityId, Locale locale) {
        return ResponseEntity.ok(degreeService.getAllDegrees(universityId, locale));
    }

    @Operation(
            description = "Update a degree by ID",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateDegree(@PathVariable UUID id, @RequestBody DegreeCreateDTO degreeCreateDTO) {
        return ResponseEntity.ok( degreeService.update(id, degreeCreateDTO));
    }

    @Operation(
            description = "Get a degree by ID",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<DegreeResponseDTO> getDegreeById(@PathVariable UUID id, Locale locale) {
        return ResponseEntity.ok(degreeService.getById(id, locale));
    }

    @Operation(
            description = "Delete a degree by ID",
            method = "DELETE method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDegree(@PathVariable UUID id) {
        return ResponseEntity.ok( degreeService.delete(id));
    }

    @Operation(
            description = "Activate a degree by ID",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/activate/{id}")
    public ResponseEntity<String> activateDegree(@PathVariable UUID id) {
        return ResponseEntity.ok(degreeService.activateDegree(id));
    }
}

