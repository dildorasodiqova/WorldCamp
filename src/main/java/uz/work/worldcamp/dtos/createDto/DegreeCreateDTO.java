package uz.work.worldcamp.dtos.createDto;
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
    private String levelUz;/// bakalavr/ magistratura / aspirantura
    private String levelRus;
    private String levelEng;

    private UUID universityId;
}
