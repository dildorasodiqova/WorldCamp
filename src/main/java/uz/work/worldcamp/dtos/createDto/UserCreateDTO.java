package uz.work.worldcamp.dtos.createDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateDTO {
    private String fullName;
    private String email;
    private String password;
    private String avatar;
    private String educationLevel;
}
