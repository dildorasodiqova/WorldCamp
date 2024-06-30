package uz.work.worldcamp.dtos.responceDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FacultyResponseDTO {
    private UUID id;
    private String name;
    private Map<String, String> management;
    private Map<String, String> images;
    private Map<String, String> videos;
    private String about;
    private List<String> level;
    private List<String> lang;
    private List<String> form;
    private int contract;
    private List<String> requirements;
    private List<String> document;
    private String data;
    private String contact;
    private Map<String, Integer> acceptance;
    private List<String> addition;
    private UUID universityId;
    protected LocalDateTime createdDate;
    protected LocalDateTime updateDate;
}
