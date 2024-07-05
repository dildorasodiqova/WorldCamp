package uz.work.worldcamp.service.facultyService;

import uz.work.worldcamp.dtos.createDto.FacultyCreateDTO;
import uz.work.worldcamp.dtos.responceDto.FacultyResponseDTO;
import uz.work.worldcamp.entities.FacultyEntity;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface FacultyService {
    List<FacultyResponseDTO> mapToResponse(List<FacultyEntity> facultyEntity, Locale locale);
    FacultyResponseDTO createFaculty(FacultyCreateDTO faculty, Locale locale);
    List<FacultyResponseDTO> getAllFaculties(UUID universityId,String searchWord, Locale locale);
    FacultyResponseDTO getFacultyById(UUID id, Locale locale);
    FacultyResponseDTO updateFaculty(UUID id, FacultyCreateDTO facultyDto, Locale locale);
    String deleteFaculty(UUID id);
    String activateFaculty(UUID id);

}
