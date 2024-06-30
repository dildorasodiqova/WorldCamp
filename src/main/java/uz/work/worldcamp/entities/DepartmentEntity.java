package uz.work.worldcamp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "departmentEntity")
public class DepartmentEntity extends BaseEntity{
    private String nameUz;
    private String nameRus;
    private String nameEng;


    @ManyToOne
    @JoinColumn(name = "university_id")
    private UniversityEntity university;
}
