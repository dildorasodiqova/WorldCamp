package uz.work.worldcamp.service.degreeService;

public class DegreeServiceImpl {
    public DegreeResponseDTO createDegree(DegreeCreateDTO degreeCreateDTO) {
        Degree degree = new Degree();
        degree.setLevel(degreeCreateDTO.getLevel());
        // Set the university based on the university ID
        // degree.setUniversity(universityService.getUniversityById(degreeCreateDTO.getUniversityId()));

        Degree savedDegree = degreeRepository.save(degree);

        return mapToResponseDTO(savedDegree);
    }

    public List<DegreeResponseDTO> getAllDegrees() {
        return degreeRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public DegreeResponseDTO getDegreeById(Long id) {
        Degree degree = degreeRepository.findById(id).orElseThrow(() -> new RuntimeException("Degree not found"));
        return mapToResponseDTO(degree);
    }

    public DegreeResponseDTO updateDegree(Long id, DegreeCreateDTO degreeCreateDTO) {
        Degree degree = degreeRepository.findById(id).orElseThrow(() -> new RuntimeException("Degree not found"));

        degree.setLevel(degreeCreateDTO.getLevel());
        // Set the university based on the university ID
        // degree.setUniversity(universityService.getUniversityById(degreeCreateDTO.getUniversityId()));

        Degree updatedDegree = degreeRepository.save(degree);
        return mapToResponseDTO(updatedDegree);
    }

    public void deleteDegree(Long id) {
        degreeRepository.deleteById(id);
    }

    private DegreeResponseDTO mapToResponseDTO(Degree degree) {
        DegreeResponseDTO degreeResponseDTO = new DegreeResponseDTO();
        degreeResponseDTO.setId(degree.getId());
        degreeResponseDTO.setLevel(degree.getLevel());
        // Set other fields as needed
        return degreeResponseDTO;
    }
}
