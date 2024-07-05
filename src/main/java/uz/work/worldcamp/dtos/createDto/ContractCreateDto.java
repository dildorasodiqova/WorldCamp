package uz.work.worldcamp.dtos.createDto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "University ID is required")
    private UUID universityId;

    @NotNull(message = "Highest amount is required")
    @Min(value = 0, message = "Highest amount must be zero or greater")
    private Integer highestAmount;

    @NotNull(message = "Average amount is required")
    @Min(value = 0, message = "Average amount must be zero or greater")
    private Integer averageAmount;

    @NotNull(message = "Minimum amount is required")
    @Min(value = 0, message = "Minimum amount must be zero or greater")
    private Integer minimumAmount;

    @NotBlank(message = "Currency is required")
    private String currency;

}


