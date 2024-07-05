package uz.work.worldcamp.dtos.createDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MailDto {
    @NotBlank(message = "Message is required")
    private String message;

    @Email(message = "Email address is invalid")
    @NotBlank(message = "Email is required")
    private String email;
}
