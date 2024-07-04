package uz.work.worldcamp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "facultyEntity")
public class FacultyEntity extends BaseEntity {
    private String nameUz;
    private String nameRus;
    private String nameEng;

    private String aboutUz;
    private String aboutRus;
    private String aboutEng;


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

    @Column(name = "university_id")
    private UUID universityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", insertable = false, updatable = false)
    private UniversityEntity university;

    public FacultyEntity(String nameUz, String nameRus, String nameEng, String aboutUz, String aboutRus, String aboutEng, String contact, String dekan, int contract, Map<String, Integer> acceptance, Map<String, String> management, List<String> level, List<String> lang, List<String> form, List<String> requirements, List<String> document, List<String> addition, Map<String, String> images, Map<String, String> videos, UUID universityId) {
        this.nameUz = nameUz;
        this.nameRus = nameRus;
        this.nameEng = nameEng;
        this.aboutUz = aboutUz;
        this.aboutRus = aboutRus;
        this.aboutEng = aboutEng;
        this.contact = contact;
        this.dekan = dekan;
        this.contract = contract;
        this.acceptance = acceptance;
        this.management = management;
        this.level = level;
        this.lang = lang;
        this.form = form;
        this.requirements = requirements;
        this.document = document;
        this.addition = addition;
        this.images = images;
        this.videos = videos;
        this.universityId = universityId;

    }
}
