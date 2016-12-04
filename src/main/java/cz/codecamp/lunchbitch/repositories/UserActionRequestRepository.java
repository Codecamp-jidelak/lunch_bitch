package cz.codecamp.lunchbitch.repositories;

import cz.codecamp.lunchbitch.entities.UserActionRequestEntity;
import cz.codecamp.lunchbitch.models.AuthToken;
import cz.codecamp.lunchbitch.models.UserAction;
import cz.codecamp.lunchbitch.models.UserActionRequestState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserActionRequestRepository extends JpaRepository<UserActionRequestEntity, Long> {
    Optional<UserActionRequestEntity> findByKey(String authKey);

    List<UserActionRequestEntity> findByAction(UserAction action);

    Optional<UserActionRequestEntity> findByEmailAndAction(String emailAddress, UserAction unsubscription);

    Optional<UserActionRequestEntity> findByEmailAndActionAndState(String emailAddress, UserAction action, UserActionRequestState state);

    void deleteByEmail(String emailAddress);
}
