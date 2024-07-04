package uz.work.worldcamp.dtos.createDto;
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
    private String nameUz;
    private String nameRus;
    private String nameEng;

    private String shortNameUz;
    private String shortNameRus;
    private String shortNameEng;

    private String aboutUz;
    private String aboutRus;
    private String aboutEng;

    private String historyUz;
    private String historyRus;
    private String historyEng;

    private String data;
    private String contact;
    private String license;
    private Integer students;
    private Integer eduCount;
    private Integer worldRating;
    private Integer anotherRating;
    private List<String> eduForm;
    private List<String> eduDirection;
    private List<String> achievements;
    private Map<String, String> management;
    private Map<String, String> images;
    private Map<String, String> videos;
    private Map<String, String> veterans;
    private List<String> requirements;
    private List<String> document;
    private Map<String, String> socials;
    private List<String> examDate;
    private List<String> scholarships;
    private List<String> level;
    private List<String> opportunities;
    private List<String> lang;
    private List<String> addition;
    private List<String> partner;
    private Location location;
    private Address address;
    private ContractEntity contract;
    private UUID countryId;
    private UUID cityId;
}
