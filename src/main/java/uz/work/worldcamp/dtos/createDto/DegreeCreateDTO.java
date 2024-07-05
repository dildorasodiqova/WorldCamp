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
public class DegreeCreateDTO {
    @NotBlank(message = "Uzbek level is required")
    private String levelUz; // bakalavr/ magistratura / aspirantura

    @NotBlank(message = "Russian level is required")
    private String levelRus;

    @NotBlank(message = "English level is required")
    private String levelEng;

    @NotNull(message = "University ID is required")
    private UUID universityId;
}
