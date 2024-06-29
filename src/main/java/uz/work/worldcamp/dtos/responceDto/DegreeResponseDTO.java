package uz.work.worldcamp.dtos.responceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DegreeResponseDTO {
    private Long id;
    private String level;
    private UniversityResponseDTO university;
}
