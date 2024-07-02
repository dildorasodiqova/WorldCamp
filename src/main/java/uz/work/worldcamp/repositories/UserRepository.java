package uz.work.worldcamp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.work.worldcamp.entities.UserEntity;
import uz.work.worldcamp.service.userService.UserService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    List<UserEntity> findAllByIsActiveTrue();

    Optional<UserEntity> findByEmail(String email);
}