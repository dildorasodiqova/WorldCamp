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
public class ContractCreateDto {
    private UUID universityId;
    private Integer highestAmount;
    private Integer averageAmount;
    private Integer minimumAmount;
    private String currency;

}


