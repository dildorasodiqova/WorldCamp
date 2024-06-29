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
    private int highestAmount;
    private int averageAmount;
    private int minimumAmount;
    private String currency;

}
