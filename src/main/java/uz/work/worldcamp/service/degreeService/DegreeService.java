package uz.work.worldcamp.service.degreeService;

import uz.work.worldcamp.dtos.createDto.DegreeCreateDTO;
import uz.work.worldcamp.dtos.responceDto.DegreeResponseDTO;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface DegreeService {
    DegreeResponseDTO createDegree(DegreeCreateDTO degreeCreateDTO, Locale locale);
    List<DegreeResponseDTO> getAllDegrees(UUID universityId, Locale locale);
    String update(UUID id, DegreeCreateDTO degreeCreateDTO);
    DegreeResponseDTO getById(UUID id, Locale locale);
    String delete(UUID id);
    String activateDegree(UUID id);
}
