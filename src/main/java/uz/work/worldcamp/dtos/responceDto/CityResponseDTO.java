package uz.work.worldcamp.dtos.responceDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CityResponseDTO {
    private Long id;
    private String name;
    private CountryResponseDTO country;
    private List<UniversityResponseDTO> universities;
}
