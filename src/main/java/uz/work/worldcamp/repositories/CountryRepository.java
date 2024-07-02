package uz.work.worldcamp.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.work.worldcamp.entities.CountryEntity;

import java.util.UUID;

public interface CountryRepository extends JpaRepository<CountryEntity, UUID> {
    @Cacheable("countryEntity")
    Boolean existsAllByNameIgnoreCase(String name);

    @Query("SELECT COUNT(c) > 0 FROM countryEntity c WHERE c.id != :id AND LOWER(c.name) = LOWER(:name)")
    boolean existsByIdNotAndName(@Param("id") UUID id, @Param("name") String name);

    @Modifying
    @Transactional
    @Query("UPDATE countryEntity c SET c.isActive = false WHERE c.id = :id")
    int deactivateCountryById(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE countryEntity c SET c.isActive = true WHERE c.id = :id")
    int activateCountryById(@Param("id") UUID id);
}
