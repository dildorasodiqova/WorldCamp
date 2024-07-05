package uz.work.worldcamp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.work.worldcamp.entities.DepartmentEntity;

import java.util.List;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, UUID> {
    @Query("SELECT d FROM departmentEntity d WHERE " +
            "(:keyword IS NOT NULL AND (" +
            "LOWER(d.nameEng) LIKE LOWER(CONCAT('%', :keyword,'%')) OR " +
            "LOWER(d.nameRus) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(d.nameUz) LIKE LOWER(CONCAT('%', :keyword, '%')))) " +
            "AND d.universityId = :universityId AND d.isActive = true")
    List<DepartmentEntity> findAllByKeywordAndUniversityId(@Param("keyword") String keyword, @Param("universityId") UUID universityId);


    @Query("SELECT COUNT(d) > 0 FROM departmentEntity d WHERE d.id != :id AND (LOWER(d.nameEng) = LOWER(:nameEng) OR LOWER(d.nameUz) = LOWER(:nameUz) OR LOWER(d.nameRus) = LOWER(:nameRus))")
    boolean existsByIdNotAndName(@Param("id") UUID id, @Param("nameEng") String nameEng, @Param("nameRus") String nameRus, @Param("nameUz") String nameUz);



    @Query("SELECT COUNT(d) > 0 FROM departmentEntity d WHERE LOWER(d.nameEng) = LOWER(:nameEng) OR LOWER(d.nameUz) = LOWER(:nameUz) OR LOWER(d.nameRus) = LOWER(:nameRus)")
    boolean existsByName(@Param("nameEng") String nameEng, @Param("nameRus") String nameRus, @Param("nameUz") String nameUz);

    @Modifying
    @Transactional
    @Query("UPDATE departmentEntity d SET d.nameEng = :nameEng, d.nameUz = :nameUz, d.nameRus = :nameRus, d.university.id = :universityId WHERE d.id = :id")
    int update(@Param("id") UUID id, @Param("nameEng") String nameEng, @Param("nameUz") String nameUz, @Param("nameRus") String nameRus, @Param("universityId") UUID universityId);

    @Modifying
    @Transactional
    @Query("UPDATE departmentEntity c SET c.isActive = false WHERE c.id = :id")
    int deactivateDepartmentById(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE departmentEntity c SET c.isActive = true WHERE c.id = :id")
    int activateDepartmentById(@Param("id") UUID id);




}
