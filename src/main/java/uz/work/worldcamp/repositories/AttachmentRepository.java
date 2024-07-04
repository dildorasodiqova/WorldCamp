package uz.work.worldcamp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.work.worldcamp.entities.Attachment;

import java.util.Optional;
import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
Optional<Attachment> findByName(String fileName);

}
