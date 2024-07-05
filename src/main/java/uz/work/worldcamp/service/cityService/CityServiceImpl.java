package uz.work.worldcamp.service.cityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.work.worldcamp.dtos.createDto.CityCreateDTO;
import uz.work.worldcamp.dtos.responceDto.CityResponseDTO;
import uz.work.worldcamp.entities.CityEntity;
import uz.work.worldcamp.exception.DataNotFoundException;
import uz.work.worldcamp.repositories.CityRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    @Override
    public CityResponseDTO createCity(CityCreateDTO dto, Locale locale) {
        log.info("Creating a new city with names: {}, {}, {}", dto.getNameEng(), dto.getNameUz(), dto.getNameRus());
        if (cityRepository.findByName(dto.getNameEng(), dto.getNameUz(), dto.getNameRus())) {
            log.warn("City with names: {}, {}, {} already exists.", dto.getNameEng(), dto.getNameUz(), dto.getNameRus());
            throw new IllegalArgumentException("City with this name already exists.");
        }
        CityEntity city = new CityEntity(dto.getNameUz(), dto.getNameEng(), dto.getNameRus(), dto.getCountryId());
        cityRepository.save(city);
        log.info("City with names: {}, {}, {} created successfully.", dto.getNameEng(), dto.getNameUz(), dto.getNameRus());
        return convertToResponseDTO(city, locale);
    }

    @Override
    public List<CityResponseDTO> getAllCities(UUID countryId, String searchWork, Locale locale) {
        log.info("Fetching all cities with countryId: {} and searchWork: {}", countryId, searchWork);
        return cityRepository.findByCountryIdAndSearchWork(countryId, searchWork).stream()
                .map(city -> {
                    String cityName = switch (locale.getLanguage()) {
                        case "uz" -> city.getNameUz();
                        case "ru" -> city.getNameRus();
                        default -> city.getNameEng();
                    };
                    return new CityResponseDTO(city.getId(), cityName, city.getCreatedDate(), city.getUpdateDate());
                })
                .collect(Collectors.toList());
    }

    @Override
    public CityResponseDTO updateCity(UUID id, CityCreateDTO dto, Locale locale) {
        log.info("Updating city with id: {}", id);
        CityEntity city = cityRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("City with id: {} not found.", id);
                    return new DataNotFoundException("City not found.");
                });

        if (cityRepository.existsByNameAndIdNot(id, dto.getNameEng(), dto.getNameUz(), dto.getNameRus())) {
            log.warn("City with names: {}, {}, {} already exists for a different id.", dto.getNameEng(), dto.getNameUz(), dto.getNameRus());
            throw new IllegalArgumentException("City with this name already exists.");
        }

        city.setNameUz(dto.getNameUz());
        city.setNameRus(dto.getNameRus());
        city.setNameEng(dto.getNameEng());
        city.setCountryId(dto.getCountryId());
        log.info("City with id: {} updated successfully.", id);
        return convertToResponseDTO(cityRepository.save(city), locale);
    }

    @Override
    public String deleteCity(UUID id) {
        log.info("Deactivating city with id: {}", id);
        int updatedRows = cityRepository.deactivateCityById(id);
        if (updatedRows > 0) {
            log.info("City with id: {} deactivated successfully.", id);
            return "City deactivated successfully.";
        } else {
            log.warn("City with id: {} not found for deactivation.", id);
            throw new DataNotFoundException("City not found with id: " + id);
        }
    }

    @Override
    public String activateCity(UUID id) {
        log.info("Activating city with id: {}", id);
        int updatedRows = cityRepository.activateCityById(id);
        if (updatedRows > 0) {
            log.info("City with id: {} activated successfully.", id);
            return "City activated successfully.";
        } else {
            log.warn("City with id: {} not found for activation.", id);
            throw new DataNotFoundException("City not found with id: " + id);
        }
    }

    private CityResponseDTO convertToResponseDTO(CityEntity city, Locale locale) {
        String cityName;

        switch (locale.getLanguage()) {
            case "uz" -> cityName = city.getNameUz();
            case "ru" -> cityName = city.getNameRus();
            default -> cityName = city.getNameEng();
        }

        return new CityResponseDTO(
                city.getId(),
                cityName,
                city.getCreatedDate(),
                city.getUpdateDate()
        );}

}
