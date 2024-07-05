package uz.work.worldcamp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.work.worldcamp.entities.UniversityEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UniversityRepository extends JpaRepository<UniversityEntity, UUID> {
    @Query("SELECT u FROM universityEntity u WHERE LOWER(u.nameEng) = LOWER(:nameEng) or LOWER(u.nameUz) = LOWER(:nameUz) or LOWER(u.nameRus) = LOWER(:nameRus)")
    Optional<UniversityEntity> findByName(
            @Param("nameEng") String nameEng,
            @Param("nameUz") String nameUz,
            @Param("nameRus") String nameRus);



    @Query("SELECT u FROM universityEntity u " +
            "WHERE (:countryId IS NULL OR u.countryId = :countryId) " +
            "AND (:cityId IS NULL OR u.cityId = :cityId) " +
            "AND (LOWER(u.nameEng) LIKE LOWER(CONCAT('%', :searchWord, '%')) " +
            "OR LOWER(u.nameRus) LIKE LOWER(CONCAT('%', :searchWord, '%')) " +
            "OR LOWER(u.nameUz) LIKE LOWER(CONCAT('%', :searchWord, '%'))) " +
            "AND u.isActive = true")
    List<UniversityEntity> findByCountryIdAndCityIdAndSearchWord(
            @Param("countryId") UUID countryId,
            @Param("cityId") UUID cityId,
            @Param("searchWord") String searchWord
    );

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM universityEntity u " +
            "WHERE u.id <> :id " +
            "AND (u.nameUz = :nameUZ OR u.nameRus = :nameRus OR u.nameEng = :nameEng)")
    boolean existsByNameAndIdNot(@Param("id") UUID id,
                                 @Param("nameEng") String nameEng,
                                 @Param("nameUz") String nameUz,
                                 @Param("nameRus") String nameRus);

    @Modifying
    @Transactional
    @Query("UPDATE universityEntity c SET c.isActive = false WHERE c.id = :id")
    int deactivateUniversityById(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE universityEntity c SET c.isActive = true WHERE c.id = :id")
    int activateUniversityById(@Param("id") UUID id);







}
