package uz.work.worldcamp.dtos.createDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FacultyCreateDTO {
    @NotBlank(message = "Uzbek name is required")
    private String nameUz;

    @NotBlank(message = "Russian name is required")
    private String nameRus;

    @NotBlank(message = "English name is required")
    private String nameEng;

    @NotBlank(message = "Uzbek about is required")
    private String aboutUz;

    @NotBlank(message = "Russian about is required")
    private String aboutRus;

    @NotBlank(message = "English about is required")
    private String aboutEng;

    @NotNull(message = "Management details are required")
    private Map<String, String> management;

    @NotNull(message = "Images are required")
    private Map<String, String> images;

    @NotNull(message = "Videos are required")
    private Map<String, String> videos;

    @NotBlank(message = "About is required")
    private String about;

    @NotEmpty(message = "Level is required")
    private List<String> level;

    @NotEmpty(message = "Language is required")
    private List<String> lang;

    @NotEmpty(message = "Form is required")
    private List<String> form;

    @Min(value = 0, message = "Contract amount must be zero or greater")
    private int contract;

    @NotBlank(message = "Dekan is required")
    private String dekan;

    @NotEmpty(message = "Requirements are required")
    private List<String> requirements;

    @NotEmpty(message = "Document list is required")
    private List<String> document;

    @NotBlank(message = "Data is required")
    private String data;

    @NotBlank(message = "Contact is required")
    private String contact;

    @NotNull(message = "Acceptance details are required")
    private Map<String, Integer> acceptance;

    @NotEmpty(message = "Addition is required")
    private List<String> addition;

    @NotNull(message = "University ID is required")
    private UUID universityId;
}
