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
    private String name;
    private String about;
    private String history;
    private String data;
    private String contact;
    private String license;
    private int students;
    private int eduCount;
    private int worldRating;

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
    private List<String> exam;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private CountryEntity country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id")
    private CityEntity city;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FacultyEntity> faculties;

//    public UniversityEntity(String name, UUID countryId, UUID cityId) {
//        this.name = name;
//        this.country = new CountryEntity();
//        this.country.setId(countryId);
//        this.city = new CityEntity();
//        this.city.setId(cityId);
//    }
}
