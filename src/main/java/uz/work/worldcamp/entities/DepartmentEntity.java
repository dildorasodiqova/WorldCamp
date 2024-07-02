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
@Entity(name = "departmentEntity")
public class DepartmentEntity extends BaseEntity{
    private String nameUz;
    private String nameRus;
    private String nameEng;

    @Column(name="university_id")
    private UUID universityId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id" ,insertable=false,updatable=false)
    private UniversityEntity university;


    public DepartmentEntity(String nameUz, String nameRus, String nameEng, UUID universityId) {
        this.nameUz = nameUz;
        this.nameRus = nameRus;
        this.nameEng = nameEng;
        this.universityId = universityId;
    }
}
