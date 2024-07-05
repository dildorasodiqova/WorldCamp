package uz.work.worldcamp.dtos.createDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentCreateDTO {
    @NotBlank(message = "Uzbek name is required")
    private String nameUz;

    @NotBlank(message = "Russian name is required")
    private String nameRus;

    @NotBlank(message = "English name is required")
    private String nameEng;

    @NotNull(message = "Faculty ID is required")
    private UUID facultyId;

    @NotNull(message = "University ID is required")
    private UUID universityId;

}
