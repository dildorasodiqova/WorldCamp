package uz.work.worldcamp.dtos.responceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentResponseDTO {
    private UUID id;
    private String name;
    private UniversityShortInfoDto university;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
