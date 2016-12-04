package cz.codecamp.lunchbitch.services.authorizationService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.codecamp.lunchbitch.entities.UnconfirmedLunchDemandEntity;
import cz.codecamp.lunchbitch.entities.UserActionRequestEntity;
import cz.codecamp.lunchbitch.models.*;
import cz.codecamp.lunchbitch.models.exceptions.AccountAlreadyExistsException;
import cz.codecamp.lunchbitch.models.exceptions.InvalidAuthKeyException;
import cz.codecamp.lunchbitch.repositories.UnconfirmedLunchDemandRepository;
import cz.codecamp.lunchbitch.repositories.UserActionRequestRepository;
import cz.codecamp.lunchbitch.services.authorizationService.crypto.AuthKeyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static cz.codecamp.lunchbitch.models.UserAction.UNSUBSCRIPTION;
import static cz.codecamp.lunchbitch.models.UserAction.UPDATE;
import static cz.codecamp.lunchbitch.models.UserAction.REGISTRATION;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private UserActionRequestRepository userActionRequestRepository;

    @Autowired
    private UnconfirmedLunchDemandRepository unconfirmedLunchDemandRepository;

    @Autowired
    private AuthKeyProvider authKeyProvider;


    @Override
    public AuthToken requestRegistrationConfirmation(LunchMenuDemand lunchMenuDemand) {
        verifyThereIsNoCompletedRegistration(lunchMenuDemand.getEmail());
        storeUnconfirmedRegistration(lunchMenuDemand);
        return generateAndSaveAuthToken(lunchMenuDemand.getEmail(), REGISTRATION);
    }

    private void verifyThereIsNoCompletedRegistration(String email) {
        Optional<UserActionRequestEntity> completedRegistration = userActionRequestRepository
                .findByEmailAndActionAndState(email, REGISTRATION, UserActionRequestState.COMPLETED);
        if (completedRegistration.isPresent()) {
            throw new AccountAlreadyExistsException();
        }
    }

    @Override
    public AuthToken requestChangeAccess(Email email) {
        return generateAndSaveAuthToken(email.getEmailAddress(), UPDATE);
    }

    @Override
    public AuthToken requestUnsubscribeAccess(Email email) {
        return findUnsubscribeAccessToken(email);
    }

    private AuthToken findUnsubscribeAccessToken(Email email) {
        UserActionRequestEntity userActionRequest = userActionRequestRepository.findByEmailAndAction(email.getEmailAddress(), UNSUBSCRIPTION)
                .orElseThrow(IllegalStateException::new);
        return new AuthToken(userActionRequest.getKey(), UNSUBSCRIPTION);
    }

    @Override
    public LunchMenuDemand authorizeRegistration(AuthToken registrationToken) {
        Email authorizedEmail = verifyToken(registrationToken);
        generateAndSaveAuthToken(authorizedEmail.getEmailAddress(), UNSUBSCRIPTION);
        return getTemporaryRegistration(authorizedEmail);
    }

    @Override
    public Email authorizeChange(AuthToken changeToken) {
        return verifyToken(changeToken);
    }

    @Override
    public Email authorizeUnsubscription(AuthToken unsubscribeToken) {
        return verifyToken(unsubscribeToken);
    }

    @Override
    public void removeAllUserActionRequestRecordsForUnsubscribedAccount(Email email) {
        userActionRequestRepository.deleteByEmail(email.getEmailAddress());
    }

    private void storeUnconfirmedRegistration(LunchMenuDemand lunchMenuDemand) {
        UnconfirmedLunchDemandEntity unconfirmedLunchDemandEntity = new UnconfirmedLunchDemandEntity(lunchMenuDemand.getEmail(), serializeLunchDemand(lunchMenuDemand));
        unconfirmedLunchDemandRepository.save(unconfirmedLunchDemandEntity);
    }

    private String serializeLunchDemand(LunchMenuDemand lunchMenuDemand) {
        try {
            return new ObjectMapper().writeValueAsString(lunchMenuDemand);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException();
        }
    }

    private AuthToken generateAndSaveAuthToken(String email, UserAction userAction) {
        String authKey = authKeyProvider.generateNextRandomAuthKey();
        userActionRequestRepository.save(UserActionRequestEntity.activateNew(email, authKey, userAction));
        return AuthToken.of(authKey, userAction);
    }

    private Email verifyToken(AuthToken token) {
        UserActionRequestEntity activeRequest = userActionRequestRepository.findByKey(token.getAuthKey())
                .filter(UserActionRequestEntity::isActive)
                .orElseThrow(InvalidAuthKeyException::new);
        UserActionRequestEntity completedRequest = activeRequest.complete();
        userActionRequestRepository.save(completedRequest);
        return Email.of(completedRequest.getEmail());
    }

    private LunchMenuDemand getTemporaryRegistration(Email authorizedEmail) {
        String serializedLunchDemand = unconfirmedLunchDemandRepository.findByEmail(authorizedEmail.getEmailAddress())
                .map(UnconfirmedLunchDemandEntity::getSerializedLunchDemand)
                .orElseThrow(IllegalStateException::new);
        return deserializeLunchDemand(serializedLunchDemand);
    }

    private LunchMenuDemand deserializeLunchDemand(String serializedLunchDemand) {
        try {
            return new ObjectMapper().readValue(serializedLunchDemand, LunchMenuDemand.class);
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }
}