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

    List<FacultyEntity> findAllByUniversityId(UUID universityId);

}
