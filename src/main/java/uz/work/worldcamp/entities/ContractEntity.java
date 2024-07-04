package uz.work.worldcamp.entities;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class ContractEntity extends BaseEntity {
    @Column(name = "university_id")
    private UUID universityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", insertable = false, updatable = false)
    private UniversityEntity university;

    private Integer highestAmount;
    private Integer averageAmount;
    private Integer minimumAmount;
    private String currency;

}
