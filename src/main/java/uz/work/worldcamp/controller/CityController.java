package uz.work.worldcamp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.work.worldcamp.dtos.createDto.CityCreateDTO;
import uz.work.worldcamp.dtos.responceDto.CityResponseDTO;
import uz.work.worldcamp.service.cityService.CityService;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cities")
public class CityController {
    private final CityService cityService;

    @Operation(
            description = "Create a new city",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<CityResponseDTO> createCity(@RequestBody CityCreateDTO dto, Locale locale) {
        CityResponseDTO createdCity = cityService.createCity(dto, locale);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCity);
    }


    @Operation(
            description = "Get all cities",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<List<CityResponseDTO>> getAllCities(
            @RequestParam(required = false) String searchWork,
            @RequestParam(required = false) UUID countryId,
            @RequestParam Locale locale) {
        List<CityResponseDTO> cities = cityService.getAllCities(countryId, searchWork, locale);
        return ResponseEntity.ok(cities);
    }

    @Operation(
            description = "Delete a city by ID",
            method = "DELETE method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable UUID id) {
        String message = cityService.deleteCity(id);
        return ResponseEntity.ok(message);
    }

    @Operation(
            description = "Activate a city by ID",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/activate/{id}")
    public ResponseEntity<String> activateCity(@PathVariable UUID id) {
        String message = cityService.activateCity(id);
        return ResponseEntity.ok(message);
    }

    @Operation(
            description = "Update a city by ID",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CityResponseDTO> updateCity(@PathVariable UUID id, @RequestBody CityCreateDTO dto, @RequestParam Locale locale) {
        CityResponseDTO updatedCity = cityService.updateCity(id, dto, locale);
        return ResponseEntity.ok(updatedCity);
    }





}
