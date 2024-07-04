package uz.work.worldcamp.service.countryService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.work.worldcamp.dtos.createDto.CountryCreateDTO;
import uz.work.worldcamp.dtos.responceDto.CountryResponseDTO;
import uz.work.worldcamp.entities.CountryEntity;
import uz.work.worldcamp.exception.DataAlreadyExistsException;
import uz.work.worldcamp.exception.DataNotFoundException;
import uz.work.worldcamp.repositories.CountryRepository;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService{
    private final CountryRepository countryRepository;
    @Override
    public CountryResponseDTO create(CountryCreateDTO dto) {
        if (countryRepository.findByName(dto.getNameEng(), dto.getNameUz(), dto.getNameRus())) {
            throw  new DataAlreadyExistsException("This country already exists! \n Please try again.");
        }
        CountryEntity save = countryRepository.save(new CountryEntity(dto.getNameUz(), dto.getNameRus(), dto.getNameEng()));
        return mapToResponseDTO(save);
    }

    @Override
    public List<CountryResponseDTO> getAllCountries() {
        return countryRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }



    @Override
    public CountryResponseDTO updateCountry(UUID id, CountryCreateDTO country) {
        CountryEntity entity = countryRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Country not found"));

        if (countryRepository.existsByIdNotAndName(id, country.getNameEng(), country.getNameUz(), country.getNameRus())) {
            throw  new DataAlreadyExistsException("This country already exists! \n Please try again.");
        }

        entity.setNameUz(country.getNameUz());
        entity.setNameRus(country.getNameRus());
        entity.setNameEng(country.getNameEng());

        countryRepository.save(entity);
        return mapToResponseDTO(entity);
    }


    public String deleteCountry(UUID id) {
        int updatedRows = countryRepository.deactivateCountryById(id);
        if (updatedRows > 0) {
            return "Country deactivated successfully.";
        } else {
            throw new DataNotFoundException("Country not found with id: " + id);
        }
    }
    public String activateCountry(UUID id) {
        int updatedRows = countryRepository.activateCountryById(id);
        if (updatedRows > 0) {
            return "Country activated successfully.";
        } else {
            throw new DataNotFoundException("Country not found with id: " + id);
        }
    }

    @Override
    public CountryEntity getById(UUID countryId) {
       return countryRepository.findById(countryId).orElseThrow(()->  new DataNotFoundException("This country not found !"));
    }


    private CountryResponseDTO mapToResponseDTO(CountryEntity country, Locale locale) {
        return new CountryResponseDTO(country.getId(), country.);

    }
}

