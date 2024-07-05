package uz.work.worldcamp.service.universityService;

import uz.work.worldcamp.dtos.createDto.UniversityCreateDTO;
import uz.work.worldcamp.dtos.responceDto.UniversityResponseDTO;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface UniversityService {
    UniversityResponseDTO createUniversity(UniversityCreateDTO dto, Locale locale);
    List<UniversityResponseDTO> getAllUniversities(UUID countryId, UUID cityId,String searchWord, Locale locale);
    UniversityResponseDTO getUniversityById(UUID id, Locale locale);
    UniversityResponseDTO updateUniversity(UUID id, UniversityCreateDTO dto, Locale locale);
    String deleteFaculty(UUID id);
    String activateFaculty(UUID id);
}
