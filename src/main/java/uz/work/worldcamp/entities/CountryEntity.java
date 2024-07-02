package uz.work.worldcamp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "countryEntity")

@Table(name = "countries", indexes = @Index(name = "idx_country_name", columnList = "name"))
public class CountryEntity extends BaseEntity{ ///  davlatlar ro'yhatini saqlash uchun
    private String nameUz;
    private String nameRus;
    private String nameEng;


    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CityEntity> cities;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UniversityEntity> universities;

    public CountryEntity(String nameUz, String nameRus, String nameEng) {
        this.nameUz = nameUz;
        this.nameRus = nameRus;
        this.nameEng = nameEng;
    }
}
