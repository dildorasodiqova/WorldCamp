package uz.work.worldcamp.service.facultyService;

public class FacultyServiceImpl {
    public FacultyResponseDTO createFaculty(FacultyCreateDTO facultyCreateDTO) {
        Faculty faculty = new Faculty();
        faculty.setName(facultyCreateDTO.getName());
        // Set the university based on the university ID
        // faculty.setUniversity(universityService.getUniversityById(facultyCreateDTO.getUniversityId()));

        Faculty savedFaculty = facultyRepository.save(faculty);

        return mapToResponseDTO(savedFaculty);
    }
    public List<FacultyResponseDTO> getAllFaculties() {
        return facultyRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public FacultyResponseDTO getFacultyById(Long id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> new RuntimeException("Faculty not found"));
        return mapToResponseDTO(faculty);
    }

    public FacultyResponseDTO updateFaculty(Long id, FacultyCreateDTO facultyCreateDTO) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> new RuntimeException("Faculty not found"));

        faculty.setName(facultyCreateDTO.getName());
        // Set the university based on the university ID
        // faculty.setUniversity(universityService.getUniversityById(facultyCreateDTO.getUniversityId()));

        Faculty updatedFaculty = facultyRepository.save(faculty);
        return mapToResponseDTO(updatedFaculty);
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    private FacultyResponseDTO mapToResponseDTO(Faculty faculty) {
        FacultyResponseDTO facultyResponseDTO = new FacultyResponseDTO();
        facultyResponseDTO.setId(faculty.getId());
        facultyResponseDTO.setName(faculty.getName());
        // Set other fields as needed
        return facultyResponseDTO;
    }
}

}
