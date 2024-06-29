package uz.work.worldcamp.dtos.createDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DegreeCreateDTO {
    private String level;
    private Long universityId;
}
