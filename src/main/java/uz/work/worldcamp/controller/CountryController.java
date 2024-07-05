package uz.work.worldcamp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.work.worldcamp.dtos.createDto.CountryCreateDTO;
import uz.work.worldcamp.dtos.responceDto.CountryResponseDTO;
import uz.work.worldcamp.service.countryService.CountryService;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/country")
public class CountryController {
    private final CountryService countryService;

    @Operation(
            description = "This method creates a new country",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<CountryResponseDTO> createCountry(@RequestBody CountryCreateDTO dto, Locale locale) {
        CountryResponseDTO createdCountry = countryService.create(dto, locale);
        return ResponseEntity.ok(createdCountry);
    }

    @Operation(
            description = "This method returns all countries",
            method = "GET method is supported"
    )
    @PermitAll
    @GetMapping("/getALl")
    public ResponseEntity<List<CountryResponseDTO>> getAllCountries(@RequestParam(required = false) String searchWord , Locale locale) {
        List<CountryResponseDTO> countries = countryService.getAllCountries(searchWord, locale);
        return ResponseEntity.ok(countries);
    }


    @Operation(
            description = "This method updates a country by UUID",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<CountryResponseDTO> updateCountryByUUID(
            @PathVariable UUID id,
            @RequestBody CountryCreateDTO dto,
            Locale locale) {
        CountryResponseDTO updatedCountry = countryService.updateCountry(id, dto, locale);
        return ResponseEntity.ok(updatedCountry);
    }

    @Operation(
            description = "This method deletes a country by UUID",
            method = "DELETE method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCountry(@PathVariable UUID id) {
        String result = countryService.deleteCountry(id);
        return ResponseEntity.ok(result);
    }


    @Operation(
            description = "This method activates a country by UUID",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/activate/{id}")
    public ResponseEntity<String> activateCountry(@PathVariable UUID id) {
        String result = countryService.activateCountry(id);
        return ResponseEntity.ok(result);
    }

}
