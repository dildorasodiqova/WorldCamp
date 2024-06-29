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
    private String name;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CityEntity> cities;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UniversityEntity> universities;

    public CountryEntity(String name) {
        this.name = name;
    }
}
