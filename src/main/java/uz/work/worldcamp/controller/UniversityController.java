package uz.work.worldcamp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.work.worldcamp.dtos.createDto.UniversityCreateDTO;
import uz.work.worldcamp.dtos.responceDto.UniversityResponseDTO;
import uz.work.worldcamp.service.universityService.UniversityService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/universities")
public class UniversityController {
    private final UniversityService universityService;
    @PostMapping
    public ResponseEntity<UniversityResponseDTO> createUniversity(@RequestBody UniversityCreateDTO universityCreateDTO) {
        return ResponseEntity.ok(universityService.createUniversity(universityCreateDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UniversityResponseDTO> getUniversity(@PathVariable Long id) {
        return ResponseEntity.ok(universityService.getUniversity(id));
    }
}
