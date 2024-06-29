package uz.work.worldcamp.service.cityService;

import uz.work.worldcamp.dtos.createDto.CityCreateDTO;
import uz.work.worldcamp.dtos.responceDto.CityResponseDTO;
import uz.work.worldcamp.entities.CityEntity;

import java.util.List;
import java.util.UUID;

public interface CityService {
    CityEntity getById(UUID cityId);
    String deleteCity(UUID id);
    String activateCity(UUID id);
    CityResponseDTO updateCity(UUID id, CityCreateDTO dto);
    CityResponseDTO getCityById(UUID id);
    List<CityResponseDTO> getAllCities();
    CityResponseDTO createCity(CityCreateDTO dto);
}
