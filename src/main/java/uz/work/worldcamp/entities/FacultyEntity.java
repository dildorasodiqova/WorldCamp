package uz.work.worldcamp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "facultyEntity")
public class FacultyEntity extends BaseEntity {
    private String name;
    private String about;
    private String data;
    private String contact;
    private String dekan;
    private Integer contract;

    @ElementCollection
    private Map<String, Integer> acceptance;

    @ElementCollection
    private Map<String, String> management;

    @ElementCollection
    private List<String> level;

    @ElementCollection
    private List<String> lang;

    @ElementCollection
    private List<String> form;

    @ElementCollection
    private List<String> requirements;

    @ElementCollection
    private List<String> document;

    @ElementCollection
    private List<String> addition;

    @ElementCollection
    private Map<String, String> images;

    @ElementCollection
    private Map<String, String> videos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private UniversityEntity university;
}
