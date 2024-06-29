package uz.work.worldcamp.service.facultyService;

import uz.work.worldcamp.dtos.createDto.FacultyCreateDTO;
import uz.work.worldcamp.dtos.responceDto.FacultyResponseDTO;
import uz.work.worldcamp.entities.FacultyEntity;

import java.util.List;
import java.util.UUID;

public interface FacultyService {
    List<FacultyResponseDTO> mapToResponse(List<FacultyEntity> facultyEntity);
    FacultyResponseDTO createFaculty(FacultyCreateDTO facultyCreateDTO);
    List<FacultyResponseDTO> getAllFaculties();
    FacultyResponseDTO getFacultyById(UUID id);
    FacultyResponseDTO updateFaculty(UUID id, FacultyCreateDTO facultyCreateDTO);
    String deleteFaculty(UUID id);
    String activateFaculty(UUID id);

}
