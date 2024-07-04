package uz.work.worldcamp.service.cityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.work.worldcamp.dtos.createDto.CityCreateDTO;
import uz.work.worldcamp.dtos.responceDto.CityResponseDTO;
import uz.work.worldcamp.dtos.responceDto.FacultyResponseDTO;
import uz.work.worldcamp.entities.CityEntity;
import uz.work.worldcamp.entities.CountryEntity;
import uz.work.worldcamp.entities.FacultyEntity;
import uz.work.worldcamp.exception.DataNotFoundException;
import uz.work.worldcamp.repositories.CityRepository;
import uz.work.worldcamp.service.countryService.CountryService;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Nodes.collect;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    @Override
    public CityResponseDTO createCity(CityCreateDTO dto, Locale locale) {
         if (cityRepository.findByName(dto.getNameEng(), dto.getNameUz(), dto.getNameRus())) {
            throw new IllegalArgumentException("City with this name already exists.");
        }
         CityEntity city = new CityEntity(dto.getNameUz(), dto.getNameEng(), dto.getNameRus(),dto.getCountryId() );
        cityRepository.save(city);
        return convertToResponseDTO(city);
    }

    @Override
    public List<CityResponseDTO> getAllCities(Locale locale) {
        return cityRepository.findAll().stream()
                .map(this::convertToResponseDTO(locale))
                .collect(Collectors.toList());
    }

    @Override
    public List<CityResponseDTO> getCitiesByCountryId(UUID countryId, Locale locale) {

        return cityRepository.findByCountry_Id(countryId).stream()
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
        CityEntity city = cityRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("City not found."));

        if (cityRepository.existsByNameAndIdNot(id, dto.getNameEng(), dto.getNameUz(), dto.getNameRus())){
            throw new IllegalArgumentException("City with this name already exists.");
        }

        city.setNameUz(dto.getNameUz());
        city.setNameRus(dto.getNameRus());
        city.setNameEng(dto.getNameEng());
        city.setCountryId(dto.getCountryId());

        return convertToResponseDTO( cityRepository.save(city), locale);
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
