package uz.work.worldcamp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.work.worldcamp.entities.FacultyCommentEntity;

import java.util.UUID;

public interface FacultyCommentRepository extends JpaRepository<FacultyCommentEntity, UUID> {



}
