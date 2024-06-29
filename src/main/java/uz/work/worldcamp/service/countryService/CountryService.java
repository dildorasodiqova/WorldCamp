package uz.work.worldcamp.service.countryService;

import uz.work.worldcamp.dtos.createDto.CountryCreateDTO;
import uz.work.worldcamp.dtos.responceDto.CountryResponseDTO;
import uz.work.worldcamp.entities.CountryEntity;

import java.util.List;
import java.util.UUID;

public interface CountryService {
    CountryResponseDTO create(CountryCreateDTO dto);
    List<CountryResponseDTO> getAllCountries();
    CountryResponseDTO updateCountry(UUID id, CountryCreateDTO countryCreateDTO);
    String deleteCountry(UUID id);

    String activateCountry(UUID id);


    CountryEntity getById(UUID countryId);
}
