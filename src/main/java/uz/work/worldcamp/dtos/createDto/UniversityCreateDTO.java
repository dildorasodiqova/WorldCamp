package uz.work.worldcamp.dtos.createDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.work.worldcamp.entities.Address;
import uz.work.worldcamp.entities.ContractEntity;
import uz.work.worldcamp.entities.ContractEntity;
import uz.work.worldcamp.entities.Location;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UniversityCreateDTO {
    @NotBlank(message = "Uzbek name is required")
    private String nameUz;

    @NotBlank(message = "Russian name is required")
    private String nameRus;

    @NotBlank(message = "English name is required")
    private String nameEng;

    @NotBlank(message = "Uzbek short name is required")
    private String shortNameUz;

    @NotBlank(message = "Russian short name is required")
    private String shortNameRus;

    @NotBlank(message = "English short name is required")
    private String shortNameEng;

    @NotBlank(message = "Uzbek about is required")
    private String aboutUz;

    @NotBlank(message = "Russian about is required")
    private String aboutRus;

    @NotBlank(message = "English about is required")
    private String aboutEng;

    @NotBlank(message = "Uzbek history is required")
    private String historyUz;

    @NotBlank(message = "Russian history is required")
    private String historyRus;

    @NotBlank(message = "English history is required")
    private String historyEng;

    @NotBlank(message = "Data is required")
    private String data;

    @NotBlank(message = "Contact is required")
    private String contact;

    @NotBlank(message = "License is required")
    private String license;

    @NotNull(message = "Number of students is required")
    @Min(value = 0, message = "Number of students must be zero or greater")
    private Integer students;

    @NotNull(message = "Number of educational counts is required")
    @Min(value = 0, message = "Number of educational counts must be zero or greater")
    private Integer eduCount;

    @NotNull(message = "World rating is required")
    @Min(value = 0, message = "World rating must be zero or greater")
    private Integer worldRating;

    @NotNull(message = "Another rating is required")
    @Min(value = 0, message = "Another rating must be zero or greater")
    private Integer anotherRating;

    @NotEmpty(message = "Educational forms are required")
    private List<String> eduForm;

    @NotEmpty(message = "Educational directions are required")
    private List<String> eduDirection;

    @NotEmpty(message = "Achievements are required")
    private List<String> achievements;

    @NotNull(message = "Management details are required")
    private Map<String, String> management;

    @NotNull(message = "Images are required")
    private Map<String, String> images;

    @NotNull(message = "Videos are required")
    private Map<String, String> videos;

    @NotNull(message = "Veterans details are required")
    private Map<String, String> veterans;

    @NotEmpty(message = "Requirements are required")
    private List<String> requirements;

    @NotEmpty(message = "Document list is required")
    private List<String> document;

    @NotNull(message = "Socials are required")
    private Map<String, String> socials;

    @NotEmpty(message = "Exam dates are required")
    private List<String> examDate;

    @NotEmpty(message = "Scholarships are required")
    private List<String> scholarships;

    @NotEmpty(message = "Level is required")
    private List<String> level;

    @NotEmpty(message = "Opportunities are required")
    private List<String> opportunities;

    @NotEmpty(message = "Languages are required")
    private List<String> lang;

    @NotEmpty(message = "Addition is required")
    private List<String> addition;

    @NotEmpty(message = "Partners are required")
    private List<String> partner;

    @NotNull(message = "Location is required")
    private Location location;

    @NotNull(message = "Address is required")
    private Address address;

    @NotNull(message = "Contract is required")
    private ContractEntity contract;

    @NotNull(message = "Country ID is required")
    private UUID countryId;

    @NotNull(message = "City ID is required")
    private UUID cityId;
}
