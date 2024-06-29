package uz.work.worldcamp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.work.worldcamp.entities.UniversityEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UniversityRepository extends JpaRepository<UniversityEntity, UUID> {
    Optional<UniversityEntity> findByNameAndCountryIdAndCityId(String name, UUID countryId, UUID cityId);
    List<UniversityEntity> findByCountryIdAndCityId(UUID countryId, UUID cityId);
}
