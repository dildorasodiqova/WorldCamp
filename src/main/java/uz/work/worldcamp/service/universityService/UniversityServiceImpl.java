package uz.work.worldcamp.service.universityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.work.worldcamp.dtos.createDto.UniversityCreateDTO;
import uz.work.worldcamp.dtos.responceDto.UniversityResponseDTO;
import uz.work.worldcamp.entities.CityEntity;
import uz.work.worldcamp.entities.CountryEntity;
import uz.work.worldcamp.entities.UniversityEntity;
import uz.work.worldcamp.repositories.UniversityRepository;
import uz.work.worldcamp.service.cityService.CityService;
import uz.work.worldcamp.service.countryService.CountryService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl {
    private final UniversityRepository universityRepository;
    private final CountryService countryService;
    private final CityService cityService;

    @Transactional
    public UniversityResponseDTO createUniversity(UUID countryId, UUID cityId, UniversityCreateDTO dto) {
        Optional<UniversityEntity> existingUniversity = universityRepository.findByNameAndCountryIdAndCityId(dto.getName(), countryId, cityId);
        if (existingUniversity.isPresent()) {
            throw new IllegalArgumentException("University with this name already exists in the city and country.");
        }
        CountryEntity country = countryService.getById(countryId);
        CityEntity city = cityService.getById(cityId);

        UniversityEntity university = new UniversityEntity(dto.getName(), country, cityId);
        universityRepository.save(university);
        return convertToResponseDTO(university);
    }

    public List<UniversityResponseDTO> getAllUniversities(UUID countryId, UUID cityId) {
        return universityRepository.findByCountryIdAndCityId(countryId, cityId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public UniversityResponseDTO getUniversityById(UUID id) {
        UniversityEntity university = universityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("University not found."));
        return convertToResponseDTO(university);
    }

    @Transactional
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

    @Transactional
    public void deleteUniversity(UUID id) {
        if (!universityRepository.existsById(id)) {
            throw new IllegalArgumentException("University not found.");
        }
        universityRepository.deleteById(id);
    }

    private UniversityResponseDTO convertToResponseDTO(UniversityEntity university) {
        return new UniversityResponseDTO(university.getId(), university.getName());
    }

}
