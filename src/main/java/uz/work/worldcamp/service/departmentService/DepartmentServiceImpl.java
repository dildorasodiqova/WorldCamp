package uz.work.worldcamp.service.departmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.work.worldcamp.dtos.createDto.DepartmentCreateDTO;
import uz.work.worldcamp.dtos.responceDto.DepartmentResponseDTO;
import uz.work.worldcamp.entities.DepartmentEntity;
import uz.work.worldcamp.exception.DataAlreadyExistsException;
import uz.work.worldcamp.exception.DataNotFoundException;
import uz.work.worldcamp.repositories.DepartmentRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{
    private final DepartmentRepository departmentRepository;
    @Override
    public DepartmentResponseDTO createDepartment(DepartmentCreateDTO department) {
        if (departmentRepository.existsByName(department.getNameEng(), department.getNameRus(), department.getNameUz())){
            throw new DataAlreadyExistsException("This department name already exists");
        }
        DepartmentEntity savedDepartment = departmentRepository.save(
                new DepartmentEntity(
                        department.getNameUz(),
                        department.getNameRus(),
                        department.getNameEng(),
                        department.getUniversityId()
                        ));

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
    public String delete(UUID id) {
        int updatedRows = departmentRepository.deactivateDepartmentById(id);
        if (updatedRows > 0) {
            return "Department deactivated successfully.";
        } else {
            throw new DataNotFoundException("Department not found with id: " + id);
        }
    }


    @Override
    public String activateDepartment(UUID id) {
        int updatedRows = departmentRepository.activateDepartmentById(id);
        if (updatedRows > 0) {
            return "Department activated successfully.";
        } else {
            throw new DataNotFoundException("Department not found with id: " + id);
        }
    }

    @Override
    public String updateDepartment(UUID id, DepartmentCreateDTO department) {
        if (departmentRepository.existsByIdNotAndName(id, department.getNameEng(), department.getNameRus(),department.getNameUz())){
            throw  new DataAlreadyExistsException("This department name already exist !");
        }
        if (departmentRepository.update(id, department.getNameEng(), department.getNameUz(), department.getNameRus(), department.getUniversityId()) == 0){
            throw new DataNotFoundException("Department not found !");
        }
        return "Successfully updated";
    }


    private DepartmentResponseDTO mapToResponseDTO(DepartmentEntity department) {
        return new DepartmentResponseDTO(department.getId(), department.get);


    }
}
