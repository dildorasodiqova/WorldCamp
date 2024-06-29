package uz.work.worldcamp.service.departmentService;

import uz.work.worldcamp.dtos.createDto.DepartmentCreateDTO;
import uz.work.worldcamp.dtos.responceDto.DepartmentResponseDTO;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {
    DepartmentResponseDTO createDepartment(DepartmentCreateDTO departmentCreateDTO);
    List<DepartmentResponseDTO> getAllDepartments();
    DepartmentResponseDTO getDepartmentById(UUID id);
    DepartmentResponseDTO updateDepartment(UUID id, DepartmentCreateDTO departmentCreateDTO);
}
