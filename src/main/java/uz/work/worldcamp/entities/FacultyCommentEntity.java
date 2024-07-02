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
@Entity(name = "facultyComment")
public class FacultyCommentEntity extends BaseEntity{
    @Column(name="user_id")
    private UUID userId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;


    @Column(name="faculty_id")
    private UUID facultyId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id", insertable = false, updatable = false)
    private FacultyEntity facultyEntity;

    @Column(name = "parent_id")
    private UUID parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private FacultyCommentEntity facultyComment; ///bu parent

    @Column(columnDefinition = "text")
    private String comment;


    public FacultyCommentEntity(UUID userId, UUID facultyId,UUID parentId, String comment) {
        this.userId = userId;
        this.facultyId =  facultyId;
        this.parentId = parentId;
        this.comment = comment;
    }



}
