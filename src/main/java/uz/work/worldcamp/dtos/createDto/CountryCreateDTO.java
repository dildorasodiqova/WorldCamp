package uz.work.worldcamp.dtos.createDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CountryCreateDTO {
    @NotBlank(message = "Uzbek name is required")
    private String nameUz;

    @NotBlank(message = "Russian name is required")
    private String nameRus;

    @NotBlank(message = "English name is required")
    private String nameEng;
}
