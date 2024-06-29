package uz.work.worldcamp.service.degreeService;

import uz.work.worldcamp.dtos.createDto.DegreeCreateDTO;
import uz.work.worldcamp.dtos.responceDto.DegreeResponseDTO;

import java.util.List;
import java.util.UUID;

public interface DegreeService {
    DegreeResponseDTO createDegree(DegreeCreateDTO degreeCreateDTO);
    List<DegreeResponseDTO> getAllDegrees();
    DegreeResponseDTO update(UUID id, DegreeCreateDTO degreeCreateDTO);
    DegreeResponseDTO getById(UUID id);
    String delete(UUID id);
    String activateDegree(UUID id);
}
