package uz.work.worldcamp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.work.worldcamp.entities.DegreeEntity;

import java.util.UUID;

public interface DegreeRepository extends JpaRepository<DegreeEntity, UUID> {
}
