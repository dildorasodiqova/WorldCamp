package uz.work.worldcamp.service.departmentService;

import uz.work.worldcamp.dtos.createDto.DepartmentCreateDTO;
import uz.work.worldcamp.dtos.responceDto.DepartmentResponseDTO;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface DepartmentService {
    DepartmentResponseDTO createDepartment(DepartmentCreateDTO department, Locale locale);
    List<DepartmentResponseDTO> getAllDepartments(String searchWord,UUID universityId, Locale locale);
    DepartmentResponseDTO getDepartmentById(UUID id, Locale locale);
    String delete(UUID id);
    String activateDepartment(UUID id);
    String updateDepartment(UUID id, DepartmentCreateDTO departmentCreateDTO);
}
