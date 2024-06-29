package uz.work.worldcamp.service.cityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.work.worldcamp.dtos.createDto.CityCreateDTO;
import uz.work.worldcamp.dtos.responceDto.CityResponseDTO;
import uz.work.worldcamp.entities.CityEntity;
import uz.work.worldcamp.entities.CountryEntity;
import uz.work.worldcamp.exception.DataNotFoundException;
import uz.work.worldcamp.repositories.CityRepository;
import uz.work.worldcamp.service.countryService.CountryService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CountryService countryService;
    @Override
    public CityResponseDTO createCity(CityCreateDTO dto) {
         if (cityRepository.existsAllByNameIgnoreCase(dto.getName())) {
            throw new IllegalArgumentException("City with this name already exists.");
        }
        CountryEntity country = countryService.getById(dto.getCountryId());
        CityEntity city = new CityEntity(dto.getName(),country );
        cityRepository.save(city);
        return convertToResponseDTO(city);
    }

    @Override
    public List<CityResponseDTO> getAllCities() {
        return cityRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CityResponseDTO getCityById(UUID id) {
        CityEntity city = cityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("City not found."));
        return convertToResponseDTO(city);
    }

    @Override
    public CityResponseDTO updateCity(UUID id, CityCreateDTO dto) {
        CityEntity city = cityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("City not found."));
        if (!city.getName().equals(dto.getName())) {
            Optional<CityEntity> existingCity = cityRepository.findByName(dto.getName());
            if (existingCity.isPresent()) {
                throw new IllegalArgumentException("City with this name already exists.");
            }
            city.setName(dto.getName());
        }
        cityRepository.save(city);
        return convertToResponseDTO(city);
    }

    @Override
    public String deleteCity(UUID id) {
        int updatedRows = cityRepository.deactivateCityById(id);
        if (updatedRows > 0) {
            return "City deactivated successfully.";
        } else {
            throw new DataNotFoundException("Country not found with id: " + id);
        }
    }
    @Override
    public String activateCity(UUID id) {
        int updatedRows = cityRepository.activateCityById(id);
        if (updatedRows > 0) {
            return "City activated successfully.";
        } else {
            throw new DataNotFoundException("City not found with id: " + id);
        }
    }

    private CityResponseDTO convertToResponseDTO(CityEntity city) {
        return new CityResponseDTO(city.getId(), city.getName());
    }

    @Override
    public CityEntity getById(UUID cityId) {
        return cityRepository.findById(cityId).orElseThrow(()->  new DataNotFoundException("This city not found !"));
    }
}
