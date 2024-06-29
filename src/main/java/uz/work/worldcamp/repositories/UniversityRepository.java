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
    Optional<UniversityEntity> findByNameAndCountryIdAndCityId(String name, UUID countryId, UUID cityId);
    List<UniversityEntity> findByCountryIdAndCityId(UUID countryId, UUID cityId);
    @Modifying
    @Transactional
    @Query("UPDATE universityEntity c SET c.isActive = false WHERE c.id = :id")
    int deactivateUniversityById(@Param("id") UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE universityEntity c SET c.isActive = true WHERE c.id = :id")
    int activateUniversityById(@Param("id") UUID id);







}
