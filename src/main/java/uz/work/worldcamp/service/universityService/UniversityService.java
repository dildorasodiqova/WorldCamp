package uz.work.worldcamp.service.universityService;

import uz.work.worldcamp.dtos.createDto.UniversityCreateDTO;
import uz.work.worldcamp.dtos.responceDto.UniversityResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UniversityService {
    UniversityResponseDTO createUniversity(UniversityCreateDTO dto);
    List<UniversityResponseDTO> getAllUniversities(UUID countryId, UUID cityId);
    UniversityResponseDTO getUniversityById(UUID id);
    UniversityResponseDTO updateUniversity(UUID id, UniversityCreateDTO dto);
    String deleteFaculty(UUID id);
    String activateFaculty(UUID id);
}
