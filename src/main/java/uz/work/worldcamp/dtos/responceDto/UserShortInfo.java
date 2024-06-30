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
public class UserShortInfo {
    private UUID id;
    private String fullName;
    private LocalDateTime createdDate;









}
