package uz.work.worldcamp.service.countryService;

import uz.work.worldcamp.dtos.createDto.CountryCreateDTO;
import uz.work.worldcamp.dtos.responceDto.CountryResponseDTO;
import uz.work.worldcamp.entities.CountryEntity;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface CountryService {
    CountryResponseDTO create(CountryCreateDTO dto, Locale locale);
    List<CountryResponseDTO> getAllCountries(String searchWord , Locale locale);
    CountryResponseDTO updateCountry(UUID id, CountryCreateDTO countryCreateDTO, Locale locale);
    String deleteCountry(UUID id);

    String activateCountry(UUID id);


    CountryResponseDTO getById(UUID countryId, Locale locale);
}
