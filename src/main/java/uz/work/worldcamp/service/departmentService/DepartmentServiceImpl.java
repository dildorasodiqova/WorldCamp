package uz.work.worldcamp.service.departmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.work.worldcamp.dtos.createDto.DepartmentCreateDTO;
import uz.work.worldcamp.dtos.responceDto.DepartmentResponseDTO;
import uz.work.worldcamp.entities.DepartmentEntity;
import uz.work.worldcamp.repositories.DepartmentRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{
    private final DepartmentRepository departmentRepository;
    @Override
    public DepartmentResponseDTO createDepartment(DepartmentCreateDTO departmentCreateDTO) {

        DepartmentEntity savedDepartment = departmentRepository.save(department);

        return mapToResponseDTO(savedDepartment);
    }

    @Override
    public List<DepartmentResponseDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentResponseDTO getDepartmentById(UUID id) {
        DepartmentEntity department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found"));
        return mapToResponseDTO(department);
    }

    @Override
    public DepartmentResponseDTO updateDepartment(UUID id, DepartmentCreateDTO departmentCreateDTO) {
        DepartmentEntity department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found"));

        department.setName(departmentCreateDTO.getName());
        // Set the university based on the university ID
        // department.setUniversity(universityService.getUniversityById(departmentCreateDTO.getUniversityId()));

        DepartmentEntity updatedDepartment = departmentRepository.save(department);
        return mapToResponseDTO(updatedDepartment);
    }



    private DepartmentResponseDTO mapToResponseDTO(DepartmentEntity department) {
        DepartmentResponseDTO departmentResponseDTO = new DepartmentResponseDTO();
        departmentResponseDTO.setId(department.getId());
        departmentResponseDTO.setName(department.getName());
        // Set other fields as needed
        return departmentResponseDTO;
    }
}
