package cz.codecamp.lunchbitch.repositories;

import cz.codecamp.lunchbitch.entities.UserActionRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserActionRequestRepository extends JpaRepository<UserActionRequestEntity, Long> {
    Optional<UserActionRequestEntity> findByKey(String authKey);
}
