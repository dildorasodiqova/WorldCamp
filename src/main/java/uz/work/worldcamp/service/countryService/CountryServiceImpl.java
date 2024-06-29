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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService{
    private final CountryRepository countryRepository;
    @Override
    public CountryResponseDTO create(CountryCreateDTO dto) {
        if (countryRepository.existsAllByNameIgnoreCase(dto.getName())) {
            throw  new DataAlreadyExistsException("This country already exists! \n Please try again.");
        }
        CountryEntity save = countryRepository.save(new CountryEntity(dto.getName()));
        return mapToResponseDTO(save);
    }

    @Override
    public List<CountryResponseDTO> getAllCountries() {
        return countryRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CountryResponseDTO getCountryById(UUID id) {
        CountryEntity country = countryRepository.findById(id).orElseThrow(() -> new RuntimeException("Country not found"));
        return mapToResponseDTO(country);
    }

    @Override
    public CountryResponseDTO updateCountry(UUID id, CountryCreateDTO countryCreateDTO) {
        if (countryRepository.existsByIdNotAndName(id, countryCreateDTO.getName())) {
            throw  new DataAlreadyExistsException("This country already exists! \n Please try again.");
        }
        CountryEntity country = countryRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Country not found"));

        country.setName(countryCreateDTO.getName());

        CountryEntity updatedCountry = countryRepository.save(country);
        return mapToResponseDTO(updatedCountry);
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


    private CountryResponseDTO mapToResponseDTO(CountryEntity country) {
        CountryResponseDTO countryResponseDTO = new CountryResponseDTO();
        countryResponseDTO.setId(country.getId());
        countryResponseDTO.setName(country.getName());
        // set other fields as needed. shu yerda kop  narsal;ari yoq. togrlash kk
        return countryResponseDTO;
    }
}
}
