package uz.work.worldcamp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.work.worldcamp.entities.ContractEntity;

import java.util.List;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<ContractEntity, UUID> {
    @Modifying
    @Transactional
    @Query("UPDATE  countryEntity c SET c.isActive = false WHERE c.id = :id")
    int deactivateContractById(UUID id);
    List<ContractEntity> findAllByUniversityIdAndIsActiveTrue(UUID universityId);
}
