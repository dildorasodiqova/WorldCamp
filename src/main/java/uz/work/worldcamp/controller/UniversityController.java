package uz.work.worldcamp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.work.worldcamp.dtos.createDto.UniversityCreateDTO;
import uz.work.worldcamp.dtos.responceDto.UniversityResponseDTO;
import uz.work.worldcamp.service.universityService.UniversityService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/universities")
public class UniversityController {
    private final UniversityService universityService;
    @Operation(
            description = "Create a new university",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<UniversityResponseDTO> createUniversity(
            @RequestParam UUID countryId,
            @RequestParam UUID cityId,
            @RequestBody UniversityCreateDTO dto) {
        UniversityResponseDTO createdUniversity = universityService.createUniversity(countryId, cityId, dto);
        return ResponseEntity.ok(createdUniversity);
    }

    @Operation(
            description = "Get all universities for a specific country and city",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UniversityResponseDTO>> getAllUniversities(
            @RequestParam UUID countryId,
            @RequestParam UUID cityId) {
        List<UniversityResponseDTO> universities = universityService.getAllUniversities(countryId, cityId);
        return ResponseEntity.ok(universities);
    }

    @Operation(
            description = "Get a university by its UUID",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UniversityResponseDTO> getUniversityById(@PathVariable UUID id) {
        UniversityResponseDTO university = universityService.getUniversityById(id);
        return ResponseEntity.ok(university);
    }

    @Operation(
            description = "Update a university by its UUID",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<UniversityResponseDTO> updateUniversity(
            @PathVariable UUID id,
            @RequestBody UniversityCreateDTO dto) {
        UniversityResponseDTO updatedUniversity = universityService.updateUniversity(id, dto);
        return ResponseEntity.ok(updatedUniversity);
    }

    @Operation(
            description = "Delete a faculty by its UUID",
            method = "DELETE method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteFaculty/{id}")
    public ResponseEntity<String> deleteFaculty(@PathVariable UUID id) {
        String message = universityService.deleteFaculty(id);
        return ResponseEntity.ok(message);
    }

    @Operation(
            description = "Activate a faculty by its UUID",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/activateFaculty/{id}")
    public ResponseEntity<String> activateFaculty(@PathVariable UUID id) {
        String message = universityService.activateFaculty(id);
        return ResponseEntity.ok(message);
    }
}
