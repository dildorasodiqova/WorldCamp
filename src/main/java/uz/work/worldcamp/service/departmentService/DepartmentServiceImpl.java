package uz.work.worldcamp.service.departmentService;

public class DepartmentServiceImpl {
    public DepartmentResponseDTO createDepartment(DepartmentCreateDTO departmentCreateDTO) {
        Department department = new Department();
        department.setName(departmentCreateDTO.getName());
        // Set the university based on the university ID
        // department.setUniversity(universityService.getUniversityById(departmentCreateDTO.getUniversityId()));

        Department savedDepartment = departmentRepository.save(department);

        return mapToResponseDTO(savedDepartment);
    }

    public List<DepartmentResponseDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public DepartmentResponseDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found"));
        return mapToResponseDTO(department);
    }

    public DepartmentResponseDTO updateDepartment(Long id, DepartmentCreateDTO departmentCreateDTO) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Department not found"));

        department.setName(departmentCreateDTO.getName());
        // Set the university based on the university ID
        // department.setUniversity(universityService.getUniversityById(departmentCreateDTO.getUniversityId()));

        Department updatedDepartment = departmentRepository.save(department);
        return mapToResponseDTO(updatedDepartment);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    private DepartmentResponseDTO mapToResponseDTO(Department department) {
        DepartmentResponseDTO departmentResponseDTO = new DepartmentResponseDTO();
        departmentResponseDTO.setId(department.getId());
        departmentResponseDTO.setName(department.getName());
        // Set other fields as needed
        return departmentResponseDTO;
    }
}
