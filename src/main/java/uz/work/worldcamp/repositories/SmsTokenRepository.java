package uz.work.worldcamp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.work.worldcamp.entities.SmsTokenEntity;

import java.util.Optional;


public interface SmsTokenRepository extends JpaRepository<SmsTokenEntity,String> {
    Optional<SmsTokenEntity> findByEmail(String email);
}
