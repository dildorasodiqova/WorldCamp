package uz.work.worldcamp.entities;

import jakarta.persistence.*;
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
@Entity(name = "universityEntity")
public class UniversityEntity extends BaseEntity{
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

    @ElementCollection
    private List<String> eduForm; ///“Kunduzgi”, “Kechki”, “Sirtqi”


    @ElementCollection
    private List<String> eduDirection;

    @ElementCollection
    private List<String> achievements;

    @ElementCollection
    private Map<String, String> management;

    @ElementCollection
    private Map<String, String> images;

    @ElementCollection
    private Map<String, String> videos;

    @ElementCollection
    private Map<String, String> veterans;

    @ElementCollection
    private List<String> requirements;

    @ElementCollection
    private List<String> document;

    @ElementCollection
    private Map<String, String> socials;

    @ElementCollection
    private List<String> examDate;

    @ElementCollection
    private List<String> scholarships;

    @ElementCollection
    private List<String> level;

    @ElementCollection
    private List<String> opportunities;

    @ElementCollection
    private List<String> lang;

    @ElementCollection
    private List<String> addition;

    @ElementCollection
    private List<String> partner;

    @Embedded
    private Location location;

    @Embedded
    private Address address;

    @Embedded
    private Contract contract;

    @Column(name="country_id")
    private UUID countryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id",insertable=false,updatable=false)
    private CountryEntity country;

    @Column(name = "city_id")
    private UUID cityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private CityEntity city;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FacultyEntity> faculties;

    public UniversityEntity(String nameUz, String nameRus, String nameEng, UUID countryId, UUID cityId) {
        this.nameUz = nameUz;
        this.nameRus = nameRus;
        this.nameEng = nameEng;
        this.countryId =  countryId;
        this.cityId = cityId;
    }
}
