package uz.work.worldcamp.dtos.responceDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContractResponseDto {
    private UUID id;
    private UUID universityId;
    private Integer highestAmount;
    private Integer averageAmount;
    private Integer minimumAmount;
    private String currency;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
