package uz.work.worldcamp.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;

public class DepartmentController {

    @Operation(
    summary = "Create a new department",
    description = "Creates a new department with the provided details."
            )
    @PostMapping("/create")
    public ResponseEntity<DepartmentResponseDTO> createDepartment(@RequestBody DepartmentCreateDTO departmentCreateDTO) {
        DepartmentResponseDTO createdDepartment = departmentService.createDepartment(departmentCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }

    @Operation(
            summary = "Get all departments",
            description = "Retrieves a list of all departments."
    )
    @GetMapping("/all")
    public ResponseEntity<List<DepartmentResponseDTO>> getAllDepartments() {
        List<DepartmentResponseDTO> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @Operation(
            summary = "Get department by ID",
            description = "Retrieves a department based on its unique identifier."
    )
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentById(@PathVariable UUID id) {
        DepartmentResponseDTO department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }

    @Operation(
            summary = "Update department",
            description = "Updates an existing department with the provided details."
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<DepartmentResponseDTO> updateDepartment(@PathVariable UUID id, @RequestBody DepartmentCreateDTO departmentCreateDTO) {
        DepartmentResponseDTO updatedDepartment = departmentService.updateDepartment(id, departmentCreateDTO);
        return ResponseEntity.ok(updatedDepartment);
    }
}
