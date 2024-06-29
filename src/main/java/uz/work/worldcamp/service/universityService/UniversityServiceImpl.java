package uz.work.worldcamp.service.universityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.work.worldcamp.dtos.createDto.UniversityCreateDTO;
import uz.work.worldcamp.dtos.responceDto.UniversityResponseDTO;
import uz.work.worldcamp.entities.CityEntity;
import uz.work.worldcamp.entities.CountryEntity;
import uz.work.worldcamp.entities.UniversityEntity;
import uz.work.worldcamp.exception.DataNotFoundException;
import uz.work.worldcamp.repositories.UniversityRepository;
import uz.work.worldcamp.service.cityService.CityService;
import uz.work.worldcamp.service.countryService.CountryService;
import uz.work.worldcamp.service.facultyService.FacultyService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService{
    private final UniversityRepository universityRepository;
    private final FacultyService facultyService;

    @Transactional
    @Override
    public UniversityResponseDTO createUniversity(UniversityCreateDTO dto) {
        Optional<UniversityEntity> existingUniversity = universityRepository.findByNameAndCountryIdAndCityId(dto.getName(), dto.getCountryId(), dto.getCityId());
        if (existingUniversity.isPresent()) {
            throw new IllegalArgumentException("University with this name already exists in the city and country.");
        }

        UniversityEntity university = new UniversityEntity(dto.getName(), dto.getCountryId(), dto.getCityId());
        universityRepository.save(university);
        return convertToResponseDTO(university);
    }

    @Override
    public List<UniversityResponseDTO> getAllUniversities(UUID countryId, UUID cityId) {
        return universityRepository.findByCountryIdAndCityId(countryId, cityId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UniversityResponseDTO getUniversityById(UUID id) {
        UniversityEntity university = universityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("University not found."));
        return convertToResponseDTO(university);
    }

    @Transactional
    @Override
    public UniversityResponseDTO updateUniversity(UUID id, UniversityCreateDTO dto) {
        UniversityEntity university = universityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("University not found."));
        if (!university.getName().equals(dto.getName())) {
            Optional<UniversityEntity> existingUniversity = universityRepository.findByNameAndCountryIdAndCityId(dto.getName(), university.getCountry().getId(), university.getCity().getId());
            if (existingUniversity.isPresent()) {
                throw new IllegalArgumentException("University with this name already exists in the city and country.");
            }
            university.setName(dto.getName());
        }
        universityRepository.save(university);
        return convertToResponseDTO(university);
    }

    @Override
    public String deleteFaculty(UUID id) {
        int updatedRows = universityRepository.deactivateUniversityById(id);
        if (updatedRows > 0) {
            return "Faculty deactivated successfully.";
        } else {
            throw new DataNotFoundException("Faculty not found with id: " + id);
        }
    }
    @Override
    public String activateFaculty(UUID id) {
        int updatedRows = universityRepository.activateUniversityById(id);
        if (updatedRows > 0) {
            return "Faculty activated successfully.";
        } else {
            throw new DataNotFoundException("Faculty not found with id: " + id);
        }
    }

    private UniversityResponseDTO convertToResponseDTO(UniversityEntity u) {
        return new UniversityResponseDTO(
                u.getId(),
                u.getName(),
                u.getAbout(),
                u.getHistory(),
                u.getData(),
                u.getContact(),
                u.getLicense(),
                u.getStudents(),
                u.getEduCount(),
                u.getWorldRating(),
                u.getAchievements(),
                u.getManagement(),
                u.getImages(),
                u.getVideos(),
                u.getVeterans(),
                u.getRequirements(),
                u.getDocument(),
                u.getSocials(),
                u.getExam(),
                u.getScholarships(),
                u.getLevel(),
                u.getOpportunities(),
                u.getLang(),
                u.getAddition(),
                u.getPartner(),
                u.getLocation(),
                u.getAddress(),
                u.getContract(),
                u.getCountryId(),
                u.getCityId(),
                facultyService.mapToResponse(u.getFaculties()));
    }

}
