package uz.work.worldcamp.dtos.createDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CityCreateDTO {
    private String nameUz;
    private String nameEng;
    private String nameRus;

    private UUID countryId;
}
