package uz.work.worldcamp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.work.worldcamp.dtos.createDto.CityCreateDTO;
import uz.work.worldcamp.dtos.responceDto.CityResponseDTO;
import uz.work.worldcamp.service.cityService.CityService;

import java.util.List;
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
    public ResponseEntity<CityResponseDTO> createCity(@RequestBody CityCreateDTO dto) {
        CityResponseDTO createdCity = cityService.createCity(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCity);
    }

    @Operation(
            description = "Get a city by ID",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CityResponseDTO> getCityById(@PathVariable UUID id) {
        CityResponseDTO city = cityService.getCityById(id);
        return ResponseEntity.ok(city);
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
    public ResponseEntity<CityResponseDTO> updateCity(@PathVariable UUID id, @RequestBody CityCreateDTO dto) {
        CityResponseDTO updatedCity = cityService.updateCity(id, dto);
        return ResponseEntity.ok(updatedCity);
    }

    @Operation(
            description = "Get all cities",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<List<CityResponseDTO>> getAllCities() {
        List<CityResponseDTO> cities = cityService.getAllCities();
        return ResponseEntity.ok(cities);
    }


}
