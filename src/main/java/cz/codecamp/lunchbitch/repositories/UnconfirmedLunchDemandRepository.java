package cz.codecamp.lunchbitch.repositories;

import cz.codecamp.lunchbitch.entities.UnconfirmedLunchDemandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnconfirmedLunchDemandRepository extends JpaRepository<UnconfirmedLunchDemandEntity, String> {
    Optional<UnconfirmedLunchDemandEntity> findByEmail(String emailAdress);
}
