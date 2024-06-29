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
@Entity(name = "degreeEntity")
public class DegreeEntity extends BaseEntity {
    private String level; // Bachelor, Master, Doctorate

    @ManyToOne
    @JoinColumn(name = "university_id")
    private UniversityEntity university;
}
