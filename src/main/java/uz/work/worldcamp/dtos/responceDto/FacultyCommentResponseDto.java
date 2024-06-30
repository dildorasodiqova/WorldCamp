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
public class FacultyCommentResponseDto {
    private UUID id;
    private UserShortInfo userId;
    private FacultyShortInfo facultyId;
    private String comment;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
