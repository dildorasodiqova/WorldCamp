package uz.work.worldcamp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.work.worldcamp.entities.FacultyCommentEntity;

import java.util.List;
import java.util.UUID;

public interface FacultyCommentRepository extends JpaRepository<FacultyCommentEntity, UUID> {
    List<FacultyCommentEntity> findAllByParentId(UUID parentId);
    List<FacultyCommentEntity> findAllByUserId(UUID userId);
    @Query("select fc from facultyComment fc where fc.parentId is null and fc.facultyId = :facultyId and fc.isActive = true")
    List<FacultyCommentEntity> findAllByFacultyIdAndIsActiveTrue(@Param("facultyId") UUID facultyId);

    @Modifying
    @Query("update facultyComment fc set fc.isActive = false where fc.id = :commentId")
    void deactivateByAdmin(@Param("commentId") UUID commentId);
    @Modifying
    @Query("UPDATE facultyComment fc SET fc.isActive = false WHERE fc.id = :commentId and fc.userId = :userId")
    void deactivateComments(@Param("commentId") UUID commentId, @Param("userId") UUID userId);


    @Modifying
    @Query("UPDATE facultyComment fc SET fc.isActive = false WHERE fc.parentId = :parentId")
    void deactivateSubComments(@Param("parentId") UUID parentId);

    @Transactional
    @Modifying
    @Query("UPDATE facultyComment fc SET fc.comment = :newComment WHERE fc.id = :id AND fc.userId = :userId")
    int updateComment(@Param("id") UUID id, @Param("userId") UUID userId, @Param("newComment") String newComment);

}
