package uz.work.worldcamp.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.work.worldcamp.entities.CityEntity;

import java.util.UUID;

public interface CityRepository extends JpaRepository<CityEntity , UUID> {

    @Cacheable("countryEntity")
    Boolean existsAllByNameIgnoreCase(String name);
    @Modifying
    @Transactional
    @Query("UPDATE cityEntity c SET c.isActive = false WHERE c.id = :id")
    int deactivateCityById(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE cityEntity c SET c.isActive = true WHERE c.id = :id")
    int activateCityById(@Param("id") UUID id);
}
