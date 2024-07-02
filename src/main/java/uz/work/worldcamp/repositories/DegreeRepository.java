package uz.work.worldcamp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.work.worldcamp.entities.DegreeEntity;

import java.util.UUID;

public interface DegreeRepository extends JpaRepository<DegreeEntity, UUID> {


    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN TRUE ELSE FALSE END FROM degreeEntity d WHERE d.levelEng = :levelEng OR d.levelUz = :levelUz OR d.levelRus = :levelRus")
    boolean existsByLevel(@Param("levelEng") String levelEng, @Param("levelUz") String levelUz, @Param("levelRus") String levelRus);
    @Modifying
    @Query("UPDATE degreeEntity d SET d.levelEng = :levelEng, d.levelUz = :levelUz, d.levelRus = :levelRus, d.university.id = :universityId WHERE d.id = :id")
    int updateDegree(@Param("id") UUID id, @Param("levelEng") String levelEng, @Param("levelUz") String levelUz, @Param("levelRus") String levelRus, @Param("universityId") UUID universityId);


    @Modifying
    @Transactional
    @Query("UPDATE degreeEntity c SET c.isActive = false WHERE c.id = :id")
    int deactivateDegreeById(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE degreeEntity c SET c.isActive = true WHERE c.id = :id")
    int activateDegreeById(@Param("id") UUID id);
}
