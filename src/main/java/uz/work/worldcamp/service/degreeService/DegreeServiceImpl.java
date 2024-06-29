package uz.work.worldcamp.service.degreeService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.work.worldcamp.dtos.createDto.DegreeCreateDTO;
import uz.work.worldcamp.dtos.responceDto.DegreeResponseDTO;
import uz.work.worldcamp.entities.DegreeEntity;
import uz.work.worldcamp.exception.DataNotFoundException;
import uz.work.worldcamp.repositories.DegreeRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DegreeServiceImpl implements DegreeService{
    private final DegreeRepository degreeRepository;
    @Override
    public DegreeResponseDTO createDegree(DegreeCreateDTO degreeCreateDTO) {
        DegreeEntity degree = new DegreeEntity();
        degree.setLevel(degreeCreateDTO.getLevel());
        // Set the university based on the university ID
        // degree.setUniversity(universityService.getUniversityById(degreeCreateDTO.getUniversityId()));

        DegreeEntity savedDegree = degreeRepository.save(degree);

        return mapToResponseDTO(savedDegree);
    }

    @Override
    public List<DegreeResponseDTO> getAllDegrees() {
        return degreeRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DegreeResponseDTO getById(UUID id) {
        DegreeEntity degree = degreeRepository.findById(id).orElseThrow(() -> new RuntimeException("Degree not found"));
        return mapToResponseDTO(degree);
    }

    @Override
    public DegreeResponseDTO update(UUID id, DegreeCreateDTO degreeCreateDTO) {
        DegreeEntity degree = degreeRepository.findById(id).orElseThrow(() -> new RuntimeException("Degree not found"));

        degree.setLevel(degreeCreateDTO.getLevel());
        // Set the university based on the university ID
        // degree.setUniversity(universityService.getUniversityById(degreeCreateDTO.getUniversityId()));

        DegreeEntity updatedDegree = degreeRepository.save(degree);
        return mapToResponseDTO(updatedDegree);
    }

    @Override
    public String delete(UUID id) {
        int updatedRows = degreeRepository.deactivateDegreeById(id);
        if (updatedRows > 0) {
            return "Degree deactivated successfully.";
        } else {
            throw new DataNotFoundException("Degree not found with id: " + id);
        }
    }
    @Override
    public String activateDegree(UUID id) {
        int updatedRows = degreeRepository.activateDegreeById(id);
        if (updatedRows > 0) {
            return "Degree activated successfully.";
        } else {
            throw new DataNotFoundException("Degree not found with id: " + id);
        }
    }

    private DegreeResponseDTO mapToResponseDTO(DegreeEntity degree) {
        DegreeResponseDTO degreeResponseDTO = new DegreeResponseDTO();
        degreeResponseDTO.setId(degree.getId());
        degreeResponseDTO.setLevel(degree.getLevel());
        // Set other fields as needed
        return degreeResponseDTO;
    }
}
