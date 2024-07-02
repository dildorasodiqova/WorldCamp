package uz.work.worldcamp.entities;

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
@Entity(name = "degreeEntity")
public class DegreeEntity extends BaseEntity {
    private String levelUz; // Bachelor, Master, Doctorate
    private String levelRus;
    private String levelEng;

    @Column(name = "university_id")
    private UUID universityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id",insertable=false,updatable=false)
    private UniversityEntity university;

    public DegreeEntity(String levelUz, String levelRus, String levelEng, UUID universityId) {
        this.levelUz = levelUz;
        this.levelRus = levelRus;
        this.levelEng = levelEng;
        this.universityId = universityId;
    }
}
