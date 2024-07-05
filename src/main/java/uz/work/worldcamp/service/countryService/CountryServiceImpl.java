package uz.work.worldcamp.service.countryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CountryServiceImpl implements CountryService{
    private final CountryRepository countryRepository;
    @Override
    public CountryResponseDTO create(CountryCreateDTO dto, Locale locale) {
        log.info("Creating country with nameEng: {}, nameUz: {}, nameRus: {}", dto.getNameEng(), dto.getNameUz(), dto.getNameRus());

        if (countryRepository.findByName(dto.getNameEng(), dto.getNameUz(), dto.getNameRus())) {
            throw  new DataAlreadyExistsException("This country already exists! \n Please try again.");
        }
        CountryEntity save = countryRepository.save(new CountryEntity(dto.getNameUz(), dto.getNameRus(), dto.getNameEng()));
        return mapToResponseDTO(save, locale);
    }

    @Override
    public List<CountryResponseDTO> getAllCountries(String searchWord , Locale locale) {
        log.info("Fetching all countries with searchWord: {}", searchWord);

        List<CountryEntity> countries;
        if (searchWord != null && !searchWord.isEmpty()) {
            countries = countryRepository.findAllBySearchWord(searchWord);
        } else {
            countries = countryRepository.findAllByIsActiveTrue();
        }
        return countries.stream()
                .map(country -> mapToResponseDTO(country, locale))
                .collect(Collectors.toList());
    }

    @Override
    public CountryResponseDTO updateCountry(UUID id, CountryCreateDTO country, Locale locale) {
        log.info("Updating country with id: {}", id);

        CountryEntity entity = countryRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Country not found"));

        if (countryRepository.existsByIdNotAndName(id, country.getNameEng(), country.getNameUz(), country.getNameRus())) {
            throw  new DataAlreadyExistsException("This country already exists! \n Please try again.");
        }

        entity.setNameUz(country.getNameUz());
        entity.setNameRus(country.getNameRus());
        entity.setNameEng(country.getNameEng());

        countryRepository.save(entity);
        return mapToResponseDTO(entity, locale);
    }

    @Override
    public String deleteCountry(UUID id) {
        log.info("Deleting country with id: {}", id);

        int updatedRows = countryRepository.deactivateCountryById(id);
        if (updatedRows > 0) {
            return "Country deactivated successfully.";
        } else {
            throw new DataNotFoundException("Country not found with id: " + id);
        }
    }

    @Override
    public String activateCountry(UUID id) {
        log.info("Activating country with id: {}", id);

        int updatedRows = countryRepository.activateCountryById(id);
        if (updatedRows > 0) {
            return "Country activated successfully.";
        } else {
            throw new DataNotFoundException("Country not found with id: " + id);
        }
    }

    @Override
    public CountryResponseDTO getById(UUID countryId, Locale locale) {
        log.info("Fetching country by id: {}", countryId);

        CountryEntity country = countryRepository.findById(countryId).orElseThrow(() -> new DataNotFoundException("This country not found !"));
        return mapToResponseDTO(country, locale);
    }


    private CountryResponseDTO mapToResponseDTO(CountryEntity country, Locale locale) {
        String name = switch (locale.getLanguage()) {
            case "uz" -> country.getNameUz();
            case "ru" -> country.getNameRus();
            default -> country.getNameEng();
        };
        return new CountryResponseDTO(country.getId(), name,country.getCreatedDate(), country.getUpdateDate());
    }
}

