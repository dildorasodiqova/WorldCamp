package uz.work.worldcamp.service.countryService;

import uz.work.worldcamp.dtos.createDto.CountryCreateDTO;
import uz.work.worldcamp.dtos.responceDto.CountryResponseDTO;
import uz.work.worldcamp.entities.CountryEntity;

import java.util.List;
import java.util.UUID;

public interface CountryService {
    CountryResponseDTO create(CountryCreateDTO dto);
    List<CountryResponseDTO> getAllCountries();
    CountryResponseDTO getCountryById(UUID id);  /// manashu method kkmas menimcha . country id bn undagi univerlarni beradigan method kk

    CountryResponseDTO updateCountry(UUID id, CountryCreateDTO countryCreateDTO);
    String deleteCountry(UUID id);

    String activateCountry(UUID id);


    CountryEntity getById(UUID countryId);
}
