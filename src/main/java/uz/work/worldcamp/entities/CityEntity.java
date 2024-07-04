package uz.work.worldcamp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "cityEntity")
public class CityEntity extends BaseEntity{
    private String nameUz;
    private String nameEng;
    private String nameRus;

    @Column(name = "country_id")
    private UUID countryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", insertable = false, updatable = false)
    private CountryEntity country;


    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UniversityEntity> universities;


    public CityEntity(String nameUz,String nameEng, String nameRus,  UUID countryId) {
        this.nameUz = nameUz;
        this.nameEng = nameEng;
        this.nameRus = nameRus;
        this.countryId = countryId;
    }
}
