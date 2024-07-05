package uz.work.worldcamp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.work.worldcamp.entities.FacultyEntity;

import java.util.List;
import java.util.UUID;

public interface FacultyRepository extends JpaRepository<FacultyEntity, UUID> {
    @Modifying
    @Transactional
    @Query("UPDATE facultyEntity c SET c.isActive = false WHERE c.id = :id")
    int deactivateFacultyById(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE facultyEntity c SET c.isActive = true WHERE c.id = :id")
    int activateFacultyById(@Param("id") UUID id);

    @Query("SELECT f FROM facultyEntity f " +
            "WHERE f.universityId = :universityId " +
            "AND (:searchWord IS NULL OR " +
            "LOWER(f.nameEng) LIKE LOWER(CONCAT('%', :searchWord, '%')) OR " +
            "LOWER(f.nameRus) LIKE LOWER(CONCAT('%', :searchWord, '%')) OR " +
            "LOWER(f.nameUz) LIKE LOWER(CONCAT('%', :searchWord, '%'))) and f.isActive= :true")
    List<FacultyEntity> findAllByUniversityId(
            @Param("universityId") UUID universityId,
            @Param("searchWord") String searchWord
    );

}
