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
public class CityCreateDTO {
    @NotBlank(message = "Uzbek name is required")
    private String nameUz;

    @NotBlank(message = "English name is required")
    private String nameEng;

    @NotBlank(message = "Russian name is required")
    private String nameRus;

    @NotNull(message = "Country ID is required")
    private UUID countryId;
}
