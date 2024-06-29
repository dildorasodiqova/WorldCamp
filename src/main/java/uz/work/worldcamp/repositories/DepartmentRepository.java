package uz.work.worldcamp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.work.worldcamp.entities.DepartmentEntity;

import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, UUID> {
}
