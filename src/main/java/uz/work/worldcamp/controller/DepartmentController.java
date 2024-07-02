package uz.work.worldcamp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.work.worldcamp.dtos.createDto.DepartmentCreateDTO;
import uz.work.worldcamp.dtos.responceDto.DepartmentResponseDTO;
import uz.work.worldcamp.service.departmentService.DepartmentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Operation(
            description = "Create a new department",
            method = "POST",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@RequestBody DepartmentCreateDTO department) {
        DepartmentResponseDTO createdDepartment = departmentService.createDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }

    @Operation(
            description = "Get all departments",
            method = "GET",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PermitAll
    @GetMapping("/getALL")
    public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @Operation(
            description = "Get a department by ID",
            method = "GET",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PermitAll
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentById(@PathVariable UUID id) {
        DepartmentResponseDTO department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }

    @Operation(
            description = "Deactivate a department by ID",
            method = "PUT",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable UUID id) {
        String result = departmentService.delete(id);
        return ResponseEntity.ok(result);
    }

    @Operation(
            description = "Activate a department by ID",
            method = "PUT",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/activate/{id}")
    public ResponseEntity<String> activateDepartment(@PathVariable UUID id) {
        String result = departmentService.activateDepartment(id);
        return ResponseEntity.ok(result);
    }

    @Operation(
            description = "Update a department by ID",
            method = "PUT",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateDepartment(@PathVariable UUID id, @RequestBody DepartmentCreateDTO departmentCreateDTO) {
        String result = departmentService.updateDepartment(id, departmentCreateDTO);
        return ResponseEntity.ok(result);
    }
}
