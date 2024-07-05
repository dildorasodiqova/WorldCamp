package uz.work.worldcamp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.work.worldcamp.entities.SmsStatusEntity;


public interface SmsStatusRepository extends JpaRepository<SmsStatusEntity, Long> {
}