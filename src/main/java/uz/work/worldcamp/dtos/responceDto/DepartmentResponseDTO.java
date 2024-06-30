package uz.work.worldcamp.dtos.responceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DepartmentResponseDTO {
    private Long id;
    private String name;
    private UniversityResponseDTO university;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
