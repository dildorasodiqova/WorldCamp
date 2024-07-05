package uz.work.worldcamp.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.work.worldcamp.entities.CountryEntity;
import uz.work.worldcamp.entities.UniversityEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CountryRepository extends JpaRepository<CountryEntity, UUID> {

    @Query("SELECT c FROM countryEntity c " +
            "WHERE LOWER(c.nameEng) LIKE LOWER(CONCAT('%', :searchWord, '%')) " +
            "OR LOWER(c.nameRus) LIKE LOWER(CONCAT('%', :searchWord, '%')) " +
            "OR LOWER(c.nameUz) LIKE LOWER(CONCAT('%', :searchWord, '%')) " +
            "AND c.isActive = true")
    List<CountryEntity> findAllBySearchWord(@Param("searchWord") String searchWord);


    @Query("SELECT COUNT(u) > 0 FROM countryEntity u WHERE LOWER(u.nameEng) = LOWER(:nameEng) or LOWER(u.nameUz) = LOWER(:nameUz) or LOWER(u.nameRus) = LOWER(:nameRus)")
    boolean findByName(@Param("nameEng") String nameEng, @Param("nameUz") String nameUz, @Param("nameRus") String nameRus);


    @Query("SELECT COUNT(c) > 0 FROM countryEntity c WHERE c.id != :id AND LOWER(c.nameUz) = LOWER(:name) or LOWER(c.nameRus) = LOWER(:nameRus) or LOWER(c.nameEng) = LOWER(:nameEng) ")
    boolean existsByIdNotAndName(@Param("id") UUID id, @Param("nameEng") String nameEng, @Param("nameUz") String nameUz, @Param("nameRus") String nameRus);

    @Modifying
    @Transactional
    @Query("UPDATE countryEntity c SET c.isActive = false WHERE c.id = :id")
    int deactivateCountryById(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE countryEntity c SET c.isActive = true WHERE c.id = :id")
    int activateCountryById(@Param("id") UUID id);

    List<CountryEntity> findAllByIsActiveTrue();


}
