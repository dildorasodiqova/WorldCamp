package uz.work.worldcamp.service.universityService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.work.worldcamp.dtos.createDto.UniversityCreateDTO;
import uz.work.worldcamp.dtos.responceDto.UniversityResponseDTO;
import uz.work.worldcamp.entities.CityEntity;
import uz.work.worldcamp.entities.CountryEntity;
import uz.work.worldcamp.entities.UniversityEntity;
import uz.work.worldcamp.exception.DataNotFoundException;
import uz.work.worldcamp.repositories.UniversityRepository;
import uz.work.worldcamp.service.cityService.CityService;
import uz.work.worldcamp.service.countryService.CountryService;
import uz.work.worldcamp.service.facultyService.FacultyService;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService{
    private final UniversityRepository universityRepository;
    private final FacultyService facultyService;

    @Transactional
    @Override
    public UniversityResponseDTO createUniversity(UniversityCreateDTO dto, Locale locale) {
        Optional<UniversityEntity> existingUniversity = universityRepository.findByName(dto.getNameEng(),dto.getNameUz(), dto.getNameRus());
        if (existingUniversity.isPresent()) {
            throw new IllegalArgumentException("University with this name already exists in the city and country.");
        }
        UniversityEntity save = universityRepository.save(new UniversityEntity(dto.getNameUz(), dto.getNameRus(), dto.getNameEng(), dto.getCountryId(), dto.getCityId()));
        return convertToResponseDTO(save, locale);
    }

    @Override
    public List<UniversityResponseDTO> getAllUniversities(UUID countryId, UUID cityId, Locale locale) {
        List<UniversityEntity> universities = universityRepository.findByCountryIdAndCityId(countryId, cityId);
        return universities.stream()
                .map(university -> convertToResponseDTO(university, locale))
                .collect(Collectors.toList());
    }

    @Override
    public UniversityResponseDTO getUniversityById(UUID id, Locale locale) {
        UniversityEntity university = universityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("University not found."));
        return convertToResponseDTO(university, locale);
    }

    @Transactional
    @Override
    public UniversityResponseDTO updateUniversity(UUID id, UniversityCreateDTO dto, Locale locale) {
        UniversityEntity entity = universityRepository.findById(id).orElseThrow(() -> new DataNotFoundException("University not found !"));

        if (universityRepository.existsByNameAndIdNot(id, dto.getNameEng(), dto.getNameUz(), dto.getNameRus())){
            throw  new DataNotFoundException("University name already exists");
        }

        UniversityEntity set = set(entity, dto);

        return convertToResponseDTO(set, locale);
    }

    private  UniversityEntity set(UniversityEntity entity, UniversityCreateDTO dto){
        // Set names
        entity.setNameUz(dto.getNameUz());
        entity.setNameRus(dto.getNameRus());
        entity.setNameEng(dto.getNameEng());

        // Set short names
        entity.setShortNameUz(dto.getShortNameUz());
        entity.setShortNameRus(dto.getShortNameRus());
        entity.setShortNameEng(dto.getShortNameEng());

        // Set about information
        entity.setAboutUz(dto.getAboutUz());
        entity.setAboutRus(dto.getAboutRus());
        entity.setAboutEng(dto.getAboutEng());

        // Set history information
        entity.setHistoryUz(dto.getHistoryUz());
        entity.setHistoryRus(dto.getHistoryRus());
        entity.setHistoryEng(dto.getHistoryEng());

        // Set additional details
        entity.setData(dto.getData());
        entity.setContact(dto.getContact());
        entity.setLicense(dto.getLicense());
        entity.setStudents(dto.getStudents());
        entity.setEduCount(dto.getEduCount());
        entity.setWorldRating(dto.getWorldRating());
        entity.setAnotherRating(dto.getAnotherRating());

        // Set element collections and maps
        entity.setEduForm(dto.getEduForm());
        entity.setEduDirection(dto.getEduDirection());
        entity.setAchievements(dto.getAchievements());
        entity.setManagement(dto.getManagement());
        entity.setImages(dto.getImages());
        entity.setVideos(dto.getVideos());
        entity.setVeterans(dto.getVeterans());
        entity.setRequirements(dto.getRequirements());
        entity.setDocument(dto.getDocument());
        entity.setSocials(dto.getSocials());
        entity.setExamDate(dto.getExamDate());
        entity.setScholarships(dto.getScholarships());
        entity.setLevel(dto.getLevel());
        entity.setOpportunities(dto.getOpportunities());
        entity.setLang(dto.getLang());
        entity.setAddition(dto.getAddition());
        entity.setPartner(dto.getPartner());

        // Set embedded objects
        entity.setLocation(dto.getLocation());
        entity.setAddress(dto.getAddress());
        entity.setContract(dto.getContract());

        // Set IDs and relationships
        entity.setCountryId(dto.getCountryId());
        entity.setCityId(dto.getCityId());

        ///buyerda facultetlarini qilmadimku
        return entity;
    }

    @Override
    public String deleteFaculty(UUID id) {
        int updatedRows = universityRepository.deactivateUniversityById(id);
        if (updatedRows > 0) {
            return "Faculty deactivated successfully.";
        } else {
            throw new DataNotFoundException("Faculty not found with id: " + id);
        }
    }
    @Override
    public String activateFaculty(UUID id) {
        int updatedRows = universityRepository.activateUniversityById(id);
        if (updatedRows > 0) {
            return "Faculty activated successfully.";
        } else {
            throw new DataNotFoundException("Faculty not found with id: " + id);
        }
    }

    private UniversityResponseDTO convertToResponseDTO(UniversityEntity u, Locale locale) {
        String name;
        String shortName;
        String about;
        String history;

        switch (locale.getLanguage()) {
            case "uz" -> {
                name = u.getNameUz();
                shortName = u.getShortNameUz();
                about = u.getAboutUz();
                history = u.getHistoryUz();
            }
            case "ru" -> {
                name = u.getNameRus();
                shortName = u.getShortNameRus();
                about = u.getAboutRus();
                history = u.getHistoryRus();
            }
            default -> {
                name = u.getNameEng();
                shortName = u.getShortNameEng();
                about = u.getAboutEng();
                history = u.getHistoryEng();
            }
        }

        return new UniversityResponseDTO(
                u.getId(),
                name,
                shortName,
                about,
                history,
                u.getData(),
                u.getContact(),
                u.getLicense(),
                u.getStudents(),
                u.getEduCount(),
                u.getWorldRating(),
                u.getAnotherRating(),
                u.getEduForm(),
                u.getEduDirection(),
                u.getAchievements(),
                u.getManagement(),
                u.getImages(),
                u.getVideos(),
                u.getVeterans(),
                u.getRequirements(),
                u.getDocument(),
                u.getSocials(),
                u.getExamDate(),
                u.getScholarships(),
                u.getLevel(),
                u.getOpportunities(),
                u.getLang(),
                u.getAddition(),
                u.getPartner(),
                u.getLocation(),
                u.getAddress(),
                u.getContract(),
                u.getCountryId(),
                u.getCityId(),
                facultyService.mapToResponse(u.getFaculties(), locale),
                u.getCreatedDate(),
                u.getUpdateDate()
        );
    }

}
