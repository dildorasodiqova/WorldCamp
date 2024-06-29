package uz.work.worldcamp.service.facultyService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.work.worldcamp.dtos.createDto.FacultyCreateDTO;
import uz.work.worldcamp.dtos.responceDto.FacultyResponseDTO;
import uz.work.worldcamp.entities.FacultyEntity;
import uz.work.worldcamp.exception.DataNotFoundException;
import uz.work.worldcamp.repositories.FacultyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService{
    private final FacultyRepository facultyRepository;
    @Override
    public FacultyResponseDTO createFaculty(FacultyCreateDTO facultyCreateDTO) {
        FacultyEntity faculty = new FacultyEntity();
        faculty.setName(facultyCreateDTO.getName());
        // Set the university based on the university ID
        // faculty.setUniversity(universityService.getUniversityById(facultyCreateDTO.getUniversityId()));

        FacultyEntity savedFaculty = facultyRepository.save(faculty);

        return mapToResponseDTO(savedFaculty);
    }
    @Override
    public List<FacultyResponseDTO> getAllFaculties() {
        return facultyRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FacultyResponseDTO getFacultyById(UUID id) {
        FacultyEntity faculty = facultyRepository.findById(id).orElseThrow(() -> new RuntimeException("Faculty not found"));
        return mapToResponseDTO(faculty);
    }

    @Override
    public FacultyResponseDTO updateFaculty(UUID id, FacultyCreateDTO facultyCreateDTO) {
        FacultyEntity faculty = facultyRepository.findById(id).orElseThrow(() -> new RuntimeException("Faculty not found"));

        faculty.setName(facultyCreateDTO.getName());
        // Set the university based on the university ID
        // faculty.setUniversity(universityService.getUniversityById(facultyCreateDTO.getUniversityId()));

        FacultyEntity updatedFaculty = facultyRepository.save(faculty);
        return mapToResponseDTO(updatedFaculty);
    }

    @Override
    public String deleteFaculty(UUID id) {
        int updatedRows = facultyRepository.deactivateFacultyById(id);
        if (updatedRows > 0) {
            return "Faculty deactivated successfully.";
        } else {
            throw new DataNotFoundException("Faculty not found with id: " + id);
        }
    }
    @Override
    public String activateFaculty(UUID id) {
        int updatedRows = facultyRepository.activateFacultyById(id);
        if (updatedRows > 0) {
            return "Faculty activated successfully.";
        } else {
            throw new DataNotFoundException("Faculty not found with id: " + id);
        }
    }


    private FacultyResponseDTO mapToResponseDTO(FacultyEntity faculty) {
        FacultyResponseDTO facultyResponseDTO = new FacultyResponseDTO();
        facultyResponseDTO.setId(faculty.getId());
        facultyResponseDTO.setName(faculty.getName());
        // Set other fields as needed
        return facultyResponseDTO;
    }

    @Override
    public List<FacultyResponseDTO> mapToResponse(List<FacultyEntity> fs) {
        List<FacultyResponseDTO> list = new ArrayList<>();
        for (FacultyEntity f : fs) {
            list.add(new FacultyResponseDTO(
                    f.getId(),
                    f.getName(),
                    f.getManagement(),
                    f.getImages(),
                    f.getVideos(),
                    f.getAbout(),
                    f.getLevel(),
                    f.getLang(),
                    f.getForm(),
                    f.getContract(),
                    f.getRequirements(),
                    f.getDocument(),
                    f.getData(),
                    f.getContact(),
                    f.getAcceptance(),
                    f.getAddition(),
                    f.getUniversity().getId()
            ));
        }
            return list;
        }
    }



