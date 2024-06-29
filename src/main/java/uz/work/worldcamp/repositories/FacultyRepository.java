package uz.work.worldcamp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.work.worldcamp.entities.FacultyEntity;

import java.util.UUID;

public interface FacultyRepository extends JpaRepository<FacultyEntity, UUID> {
}
