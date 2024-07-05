package uz.work.worldcamp.service.departmentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.work.worldcamp.dtos.createDto.DepartmentCreateDTO;
import uz.work.worldcamp.dtos.responceDto.DepartmentResponseDTO;
import uz.work.worldcamp.dtos.responceDto.UniversityShortInfoDto;
import uz.work.worldcamp.entities.DepartmentEntity;
import uz.work.worldcamp.entities.UniversityEntity;
import uz.work.worldcamp.exception.DataAlreadyExistsException;
import uz.work.worldcamp.exception.DataNotFoundException;
import uz.work.worldcamp.repositories.DepartmentRepository;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentResponseDTO createDepartment(DepartmentCreateDTO department, Locale locale) {
        if (departmentRepository.existsByName(department.getNameEng(), department.getNameRus(), department.getNameUz())) {
            throw new DataAlreadyExistsException("This department name already exists");
        }
        DepartmentEntity savedDepartment = departmentRepository.save(
                new DepartmentEntity(
                        department.getNameUz(),
                        department.getNameRus(),
                        department.getNameEng(),
                        department.getUniversityId()
                ));

        log.info("Department created: {}", savedDepartment.getId());

        return mapToResponseDTO(savedDepartment, locale);
    }

    @Override
    public List<DepartmentResponseDTO> getAllDepartments(String searchWord, UUID universityId, Locale locale) {
        log.info("Fetching all departments with searchWord: {} and universityId: {}", searchWord, universityId);

        return departmentRepository.findAllByKeywordAndUniversityId(searchWord, universityId).stream()
                .map(department -> mapToResponseDTO(department, locale))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentResponseDTO getDepartmentById(UUID id, Locale locale) {
        log.info("Fetching department by id: {}", id);

        DepartmentEntity department = departmentRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Department not found"));
        return mapToResponseDTO(department, locale);
    }

    @Override
    public String delete(UUID id) {
        int updatedRows = departmentRepository.deactivateDepartmentById(id);
        if (updatedRows > 0) {
            log.info("Department deactivated successfully: {}", id);
            return "Department deactivated successfully.";
        } else {
            log.error("Department not found with id: {}", id);
            throw new DataNotFoundException("Department not found with id: " + id);
        }
    }

    @Override
    public String activateDepartment(UUID id) {
        int updatedRows = departmentRepository.activateDepartmentById(id);
        if (updatedRows > 0) {
            log.info("Department activated successfully: {}", id);
            return "Department activated successfully.";
        } else {
            log.error("Department not found with id: {}", id);
            throw new DataNotFoundException("Department not found with id: " + id);
        }
    }

    @Override
    public String updateDepartment(UUID id, DepartmentCreateDTO department) {
        if (departmentRepository.existsByIdNotAndName(id, department.getNameEng(), department.getNameRus(), department.getNameUz())) {
            log.error("Department name already exists: {}", department.getNameEng());
            throw new DataAlreadyExistsException("This department name already exists !");
        }
        if (departmentRepository.update(id, department.getNameEng(), department.getNameUz(), department.getNameRus(), department.getUniversityId()) == 0) {
            log.error("Department not found: {}", id);
            throw new DataNotFoundException("Department not found !");
        }
        log.info("Department updated successfully: {}", id);
        return "Successfully updated";
    }

    private DepartmentResponseDTO mapToResponseDTO(DepartmentEntity department, Locale locale) {
        String departmentName = switch (locale.getLanguage()) {
            case "uz" -> department.getNameUz();
            case "ru" -> department.getNameRus();
            default -> department.getNameEng();
        };

        UniversityEntity university = department.getUniversity();
        String universityName = switch (locale.getLanguage()) {
            case "uz" -> university.getNameUz();
            case "ru" -> university.getNameRus();
            default -> university.getNameEng();
        };

        return new DepartmentResponseDTO(
                department.getId(),
                departmentName,
                new UniversityShortInfoDto(
                        department.getUniversityId(),
                        universityName,
                        department.getUniversity().getCreatedDate(),
                        department.getUniversity().getUpdateDate()
                ),
                department.getCreatedDate(),
                department.getUpdateDate()
        );
    }
}
