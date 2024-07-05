package uz.work.worldcamp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.work.worldcamp.dtos.createDto.FacultyCreateDTO;
import uz.work.worldcamp.dtos.responceDto.FacultyResponseDTO;
import uz.work.worldcamp.service.facultyService.FacultyService;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/faculties")
public class FacultyController {
    private final FacultyService facultyService;

    @Operation(
            description = "This method creates a new faculty",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<FacultyResponseDTO> createFaculty(@Valid @RequestBody FacultyCreateDTO faculty, @RequestParam Locale locale) {
        FacultyResponseDTO response = facultyService.createFaculty(faculty, locale);
        return ResponseEntity.ok(response);
    }

    @Operation(
            description = "This method gets all faculties by university ID",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN", "USER"})
    )
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/university/{universityId}")
    public ResponseEntity<List<FacultyResponseDTO>> getAllFaculties(
            @PathVariable UUID universityId,
             @RequestParam(required = false) String searchWord,
             Locale locale) {
        List<FacultyResponseDTO> response = facultyService.getAllFaculties(universityId, searchWord, locale);
        return ResponseEntity.ok(response);
    }

    @Operation(
            description = "This method gets a faculty by ID",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN", "USER"})
    )
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<FacultyResponseDTO> getFacultyById(@PathVariable UUID id,  Locale locale) {
        FacultyResponseDTO response = facultyService.getFacultyById(id, locale);
        return ResponseEntity.ok(response);
    }

    @Operation(
            description = "This method updates a faculty by ID",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<FacultyResponseDTO> updateFaculty(@PathVariable UUID id,@Valid @RequestBody FacultyCreateDTO facultyDto,  Locale locale) {
        FacultyResponseDTO response = facultyService.updateFaculty(id, facultyDto, locale);
        return ResponseEntity.ok(response);
    }

    @Operation(
            description = "This method deletes a faculty by ID",
            method = "DELETE method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFaculty(@PathVariable UUID id) {
        String response = facultyService.deleteFaculty(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            description = "This method activates a faculty by ID",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/activate")
    public ResponseEntity<String> activateFaculty(@PathVariable UUID id) {
        String response = facultyService.activateFaculty(id);
        return ResponseEntity.ok(response);
    }
}
