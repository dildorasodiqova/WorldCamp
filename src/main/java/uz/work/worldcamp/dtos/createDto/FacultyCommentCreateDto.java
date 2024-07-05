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
public class FacultyCommentCreateDto {
    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotNull(message = "Faculty ID is required")
    private UUID facultyId;

    private UUID parentId;

    @NotBlank(message = "Comment is required")
    private String comment;


}
