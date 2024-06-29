package uz.work.worldcamp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.work.worldcamp.entities.DegreeEntity;

import java.util.UUID;

public interface DegreeRepository extends JpaRepository<DegreeEntity, UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE degreeEntity c SET c.isActive = false WHERE c.id = :id")
    int deactivateDegreeById(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE degreeEntity c SET c.isActive = true WHERE c.id = :id")
    int activateDegreeById(@Param("id") UUID id);
}
