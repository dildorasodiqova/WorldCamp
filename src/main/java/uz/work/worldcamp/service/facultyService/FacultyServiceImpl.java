package uz.work.worldcamp.service.facultyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.work.worldcamp.dtos.createDto.FacultyCreateDTO;
import uz.work.worldcamp.dtos.responceDto.FacultyResponseDTO;
import uz.work.worldcamp.entities.FacultyEntity;
import uz.work.worldcamp.exception.DataNotFoundException;
import uz.work.worldcamp.repositories.FacultyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class FacultyServiceImpl implements FacultyService{
    private final FacultyRepository facultyRepository;
    @Override
    public FacultyResponseDTO createFaculty(FacultyCreateDTO faculty, Locale locale) {
        log.info("Creating faculty with names: {}, {}, {}", faculty.getNameEng(), faculty.getNameUz(), faculty.getNameRus());
        FacultyEntity savedFaculty = facultyRepository.save(
                new FacultyEntity(
                        faculty.getNameUz(),
                        faculty.getNameRus(),
                        faculty.getNameEng(),
                        faculty.getAboutUz(),
                        faculty.getAboutRus(),
                        faculty.getAboutEng(),
                        faculty.getContact(),
                        faculty.getDekan(),
                        faculty.getContract(),
                        faculty.getAcceptance(),
                        faculty.getManagement(),
                        faculty.getLevel(),
                        faculty.getLang(),
                        faculty.getForm(),
                        faculty.getRequirements(),
                        faculty.getDocument(),
                        faculty.getAddition(),
                        faculty.getImages(),
                        faculty.getVideos(),
                        faculty.getUniversityId()
                ));
        log.info("Faculty created successfully with ID: {}", savedFaculty.getId());
        return mapToResponseDTO(savedFaculty, locale);
    }

    @Override
    public List<FacultyResponseDTO> getAllFaculties(UUID universityId,String searchWord,  Locale locale) {
        log.info("Fetching all faculties for universityId: {}", universityId);
        List<FacultyEntity> all = facultyRepository.findAllByUniversityId(universityId, searchWord);
        return mapToResponse(all, locale);
    }

    @Override
    public FacultyResponseDTO getFacultyById(UUID id, Locale locale) {
        log.info("Fetching faculty with ID: {}", id);
        FacultyEntity faculty = facultyRepository.findById(id).orElseThrow(() -> new RuntimeException("Faculty not found"));
        log.info("Faculty found with ID: {}", id);
        return mapToResponseDTO(faculty, locale);
    }

    @Override
    public FacultyResponseDTO updateFaculty(UUID id, FacultyCreateDTO facultyDto, Locale locale) {
        log.info("Updating faculty with ID: {}", id);
        FacultyEntity faculty = facultyRepository.findById(id).orElseThrow(() -> new RuntimeException("Faculty not found"));

        FacultyEntity updatedFaculty = set(faculty, facultyDto);
        FacultyEntity savedFaculty = facultyRepository.save(updatedFaculty);
        log.info("Faculty updated successfully with ID: {}", savedFaculty.getId());
        return mapToResponseDTO(savedFaculty, locale);
    }

    private FacultyEntity set(FacultyEntity faculty, FacultyCreateDTO facultyDto) {
        log.info("Setting faculty details for: {}, {}, {}", facultyDto.getNameEng(), facultyDto.getNameUz(), facultyDto.getNameRus());
        faculty.setNameUz(facultyDto.getNameUz());
        faculty.setNameRus(facultyDto.getNameRus());
        faculty.setNameEng(facultyDto.getNameEng());

        faculty.setAboutUz(facultyDto.getAboutUz());
        faculty.setAboutRus(facultyDto.getAboutRus());
        faculty.setAboutEng(facultyDto.getAboutEng());

        faculty.setContact(facultyDto.getContact());
        faculty.setDekan(facultyDto.getDekan());
        faculty.setContract(facultyDto.getContract());

        faculty.setAcceptance(facultyDto.getAcceptance());
        faculty.setManagement(facultyDto.getManagement());

        faculty.setLevel(facultyDto.getLevel());
        faculty.setLang(facultyDto.getLang());
        faculty.setForm(facultyDto.getForm());
        faculty.setRequirements(facultyDto.getRequirements());
        faculty.setDocument(facultyDto.getDocument());
        faculty.setAddition(facultyDto.getAddition());

        faculty.setImages(facultyDto.getImages());
        faculty.setVideos(facultyDto.getVideos());

        faculty.setUniversityId(facultyDto.getUniversityId());
        return faculty;
    }

    @Override
    public String deleteFaculty(UUID id) {
        log.info("Deactivating faculty with ID: {}", id);
        int updatedRows = facultyRepository.deactivateFacultyById(id);
        if (updatedRows > 0) {
            log.info("Faculty deactivated successfully with ID: {}", id);
            return "Faculty deactivated successfully.";
        } else {
            log.warn("Faculty not found with ID: {}", id);
            throw new DataNotFoundException("Faculty not found with id: " + id);
        }
    }

    @Override
    public String activateFaculty(UUID id) {
        log.info("Activating faculty with ID: {}", id);
        int updatedRows = facultyRepository.activateFacultyById(id);
        if (updatedRows > 0) {
            log.info("Faculty activated successfully with ID: {}", id);
            return "Faculty activated successfully.";
        } else {
            log.warn("Faculty not found with ID: {}", id);
            throw new DataNotFoundException("Faculty not found with id: " + id);
        }
    }



    private FacultyResponseDTO mapToResponseDTO(FacultyEntity f, Locale locale) {
        String name;
        String about;

        switch (locale.getLanguage()) {
            case "uz" -> {
                name = f.getNameUz();
                about = f.getAboutUz();
            }
            case "ru" -> {
                name = f.getNameRus();
                about = f.getAboutRus();
            }
            default -> {
                name = f.getNameEng();
                about = f.getAboutEng();
            }
        }
        return new FacultyResponseDTO(
                f.getId(),
                name,
                f.getManagement(),
                f.getImages(),
                f.getVideos(),
                about,
                f.getLevel(),
                f.getLang(),
                f.getForm(),
                f.getContract(),
                f.getDekan(),
                f.getRequirements(),
                f.getDocument(),
                f.getContact(),
                f.getAcceptance(),
                f.getAddition(),
                f.getUniversity().getId(),
                f.getCreatedDate(),
                f.getUpdateDate()
        );
    }

    @Override
    public List<FacultyResponseDTO> mapToResponse(List<FacultyEntity> fs, Locale locale) {
        List<FacultyResponseDTO> list = new ArrayList<>();
        for (FacultyEntity f : fs) {
            String name;
            String about;

            switch (locale.getLanguage()) {
                case "uz":
                    name = f.getNameUz();
                    about = f.getAboutUz();
                    break;
                case "ru":
                    name = f.getNameRus();
                    about = f.getAboutRus();
                    break;
                default:
                    name = f.getNameEng();
                    about = f.getAboutEng();
                    break;
            }

            list.add(new FacultyResponseDTO(
                    f.getId(),
                    name,
                    f.getManagement(),
                    f.getImages(),
                    f.getVideos(),
                    about,
                    f.getLevel(),
                    f.getLang(),
                    f.getForm(),
                    f.getContract(),
                    f.getDekan(),
                    f.getRequirements(),
                    f.getDocument(),
                    f.getContact(),
                    f.getAcceptance(),
                    f.getAddition(),
                    f.getUniversity().getId(),
                    f.getCreatedDate(),
                    f.getUpdateDate()
            ));
        }
        return list;
        }
    }



