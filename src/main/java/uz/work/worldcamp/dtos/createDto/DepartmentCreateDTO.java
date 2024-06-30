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
public class DepartmentCreateDTO {
    private String nameUz;
    private String nameRus;
    private String nameEng;

    private UUID facultyId;
    private UUID universityId;
}
