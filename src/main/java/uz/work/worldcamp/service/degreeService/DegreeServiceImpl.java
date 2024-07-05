package uz.work.worldcamp.service.degreeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.work.worldcamp.dtos.createDto.DegreeCreateDTO;
import uz.work.worldcamp.dtos.responceDto.DegreeResponseDTO;
import uz.work.worldcamp.dtos.responceDto.UniversityShortInfoDto;
import uz.work.worldcamp.entities.DegreeEntity;
import uz.work.worldcamp.exception.DataNotFoundException;
import uz.work.worldcamp.repositories.DegreeRepository;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DegreeServiceImpl implements DegreeService {
    private final DegreeRepository degreeRepository;

    @Override
    public DegreeResponseDTO createDegree(DegreeCreateDTO degree, Locale locale) {
        log.info("Creating degree with levelEng: {}, levelUz: {}, levelRus: {}, universityId: {}", degree.getLevelEng(), degree.getLevelUz(), degree.getLevelRus(), degree.getUniversityId());

        if (degreeRepository.existsByLevel(degree.getLevelEng(), degree.getLevelUz(), degree.getLevelRus())) {
            throw new RuntimeException("This degree level already exists");
        }

        DegreeEntity savedDegree = degreeRepository.save(new DegreeEntity(degree.getLevelUz(), degree.getLevelRus(), degree.getLevelEng(), degree.getUniversityId()));
        return mapToResponseDTO(savedDegree, locale);
    }

    @Override
    public List<DegreeResponseDTO> getAllDegrees(UUID universityId, Locale locale) {
        log.info("Fetching all degrees for universityId: {}", universityId);

        return degreeRepository.findAllByUniversityIdAndIsActiveTrue(universityId).stream()
                .map(degree -> mapToResponseDTO(degree, locale))
                .collect(Collectors.toList());
    }

    @Override
    public DegreeResponseDTO getById(UUID id, Locale locale) {
        log.info("Fetching degree by id: {}", id);

        DegreeEntity degree = degreeRepository.findById(id).orElseThrow(() -> new RuntimeException("Degree not found"));
        return mapToResponseDTO(degree, locale);
    }

    @Transactional
    @Override
    public String update(UUID id, DegreeCreateDTO degree) {
        log.info("Updating degree with id: {} to levelEng: {}, levelUz: {}, levelRus: {}, universityId: {}", id, degree.getLevelEng(), degree.getLevelUz(), degree.getLevelRus(), degree.getUniversityId());

        int updatedRows = degreeRepository.updateDegree(id, degree.getLevelEng(), degree.getLevelUz(), degree.getLevelRus(), degree.getUniversityId());
        if (updatedRows == 0) {
            throw new RuntimeException("Degree update failed");
        }
        return "Successfully updated degree.";
    }

    @Override
    public String delete(UUID id) {
        log.info("Deleting degree with id: {}", id);

        int updatedRows = degreeRepository.deactivateDegreeById(id);
        if (updatedRows > 0) {
            return "Degree deactivated successfully.";
        } else {
            throw new DataNotFoundException("Degree not found with id: " + id);
        }
    }

    @Override
    public String activateDegree(UUID id) {
        log.info("Activating degree with id: {}", id);

        int updatedRows = degreeRepository.activateDegreeById(id);
        if (updatedRows > 0) {
            return "Degree activated successfully.";
        } else {
            throw new DataNotFoundException("Degree not found with id: " + id);
        }
    }
    private DegreeResponseDTO mapToResponseDTO(DegreeEntity degree, Locale locale) {
        String level;
        String name;
        switch (locale.getLanguage()) {
            case "uz" -> {
                level = degree.getLevelUz();
                name = degree.getUniversity().getNameUz();
            }
            case "ru" -> {
                level = degree.getLevelRus();
                name = degree.getUniversity().getNameRus();
            }
            default -> {
                level = degree.getLevelEng();
                name = degree.getUniversity().getNameEng();
            }
        }
        return new DegreeResponseDTO(
                degree.getId(),
                level,
                new UniversityShortInfoDto(
                        degree.getUniversity().getId(),
                        name,
                        degree.getUniversity().getCreatedDate(),
                        degree.getUniversity().getUpdateDate()
                ),
                degree.getCreatedDate(),
                degree.getUpdateDate()
        );
    }
}
