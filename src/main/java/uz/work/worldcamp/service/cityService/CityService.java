package uz.work.worldcamp.service.cityService;

import uz.work.worldcamp.dtos.createDto.CityCreateDTO;
import uz.work.worldcamp.dtos.responceDto.CityResponseDTO;
import uz.work.worldcamp.entities.CityEntity;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface CityService {
    CityResponseDTO createCity(CityCreateDTO dto, Locale locale);
    String deleteCity(UUID id);
    String activateCity(UUID id);
    CityResponseDTO updateCity(UUID id, CityCreateDTO dto, Locale locale);
     List<CityResponseDTO> getAllCities(UUID countryId, String searchWork, Locale locale);
}
