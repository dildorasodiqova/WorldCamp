package uz.work.worldcamp.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Contract {
    private Integer highestAmount;
    private Integer averageAmount;
    private Integer minimumAmount;
    private String currency;

}
