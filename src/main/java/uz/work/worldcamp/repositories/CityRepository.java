package uz.work.worldcamp.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.work.worldcamp.entities.CityEntity;
import uz.work.worldcamp.entities.UniversityEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CityRepository extends JpaRepository<CityEntity , UUID> {
    @Query("SELECT c FROM cityEntity c WHERE (:countryId IS NULL OR c.country.id = :countryId) " +
            "AND (:searchWork IS NULL OR LOWER(c.nameEng) LIKE LOWER(CONCAT('%', :searchWork, '%')) " +
            "OR LOWER(c.nameRus) LIKE LOWER(CONCAT('%', :searchWork, '%')) " +
            "OR LOWER(c.nameUz) LIKE LOWER(CONCAT('%', :searchWork, '%')))")
    List<CityEntity> findByCountryIdAndSearchWork(@Param("countryId") UUID countryId,
                                                  @Param("searchWork") String searchWork);
    @Query("SELECT COUNT(c) > 0 FROM cityEntity c WHERE LOWER(c.nameEng) = LOWER(:nameEng) or LOWER(c.nameUz) = LOWER(:nameUz) or LOWER(c.nameRus) = LOWER(:nameRus)")
    boolean findByName(@Param("nameEng") String nameEng, @Param("nameUz") String nameUz, @Param("nameRus") String nameRus);

    @Modifying
    @Transactional
    @Query("UPDATE cityEntity c SET c.isActive = false WHERE c.id = :id")
    int deactivateCityById(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE cityEntity c SET c.isActive = true WHERE c.id = :id")
    int activateCityById(@Param("id") UUID id);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM universityEntity u " +
            "WHERE u.id <> :id " +
            "AND (u.nameUz = :nameUZ OR u.nameRus = :nameRus OR u.nameEng = :nameEng)")
    boolean existsByNameAndIdNot(@Param("id") UUID id,@Param("nameEng") String nameEng, @Param("nameUz") String nameUz, @Param("nameRus") String nameRus);


    @Cacheable
    List<CityEntity> findByCountry_Id(UUID countryId);

}
