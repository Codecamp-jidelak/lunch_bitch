package cz.codecamp.lunchbitch.services.authorizationService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.codecamp.lunchbitch.entities.UnconfirmedLunchDemandEntity;
import cz.codecamp.lunchbitch.entities.UserActionRequestEntity;
import cz.codecamp.lunchbitch.models.*;
import cz.codecamp.lunchbitch.models.exceptions.AccountAlreadyExistsException;
import cz.codecamp.lunchbitch.models.exceptions.AccountDoesNotExistException;
import cz.codecamp.lunchbitch.models.exceptions.AccountNotActivatedException;
import cz.codecamp.lunchbitch.models.exceptions.InvalidTokenException;
import cz.codecamp.lunchbitch.repositories.UnconfirmedLunchDemandRepository;
import cz.codecamp.lunchbitch.repositories.UserActionRequestRepository;
import cz.codecamp.lunchbitch.services.authorizationService.crypto.AuthKeyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static cz.codecamp.lunchbitch.models.UserAction.*;
import static cz.codecamp.lunchbitch.models.UserActionRequestState.EXPIRED;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private UserActionRequestRepository userActionRequestRepository;

    @Autowired
    private UnconfirmedLunchDemandRepository unconfirmedLunchDemandRepository;

    @Autowired
    private AuthKeyProvider authKeyProvider;


    @Override
    public AuthToken requestRegistrationConfirmation(LunchMenuDemand lunchMenuDemand) throws AccountNotActivatedException, AccountAlreadyExistsException {
        verifyThereIsNoActiveRegistration(lunchMenuDemand.getEmail());
        verifyThereIsNoCompletedRegistration(lunchMenuDemand.getEmail());
        storeUnconfirmedRegistration(lunchMenuDemand);
        return generateAndSaveAuthToken(lunchMenuDemand.getEmail(), REGISTRATION);
    }

    @Override
    public AuthToken requestChangeAccess(Email email) {
        verifyThereIsCompletedRegistration(email.getEmailAddress());
        return generateAndSaveAuthToken(email.getEmailAddress(), UPDATE);
    }

    private void verifyThereIsCompletedRegistration(String email) {
        UserActionRequestEntity registrationRequest = userActionRequestRepository
                .findByEmailAndAction(email, REGISTRATION)
                .filter(e -> e.getState() != EXPIRED)
                .orElseThrow(AccountDoesNotExistException::new);
        if (registrationRequest.isActive()) {
            throw new AccountNotActivatedException();
        }
    }

    @Override
    public AuthToken requestUnsubscribeAccess(Email email) {
        return findUnsubscribeAccessToken(email);
    }

    @Override
    public LunchMenuDemand authorizeRegistration(AuthToken registrationToken) throws InvalidTokenException, IllegalStateException {
        Email authorizedEmail = verifyToken(registrationToken);
        generateAndSaveAuthToken(authorizedEmail.getEmailAddress(), UNSUBSCRIPTION);
        return getTemporaryRegistration(authorizedEmail);
    }

    @Override
    public Email authorizeChange(AuthToken changeToken) throws InvalidTokenException {
        return verifyToken(changeToken);
    }

    @Override
    public Email authorizeUnsubscription(AuthToken unsubscribeToken) throws InvalidTokenException {
        return verifyToken(unsubscribeToken);
    }

    @Override
    public void removeAllUserActionRequestRecordsForUnsubscribedAccount(Email email) {
        userActionRequestRepository.deleteByEmail(email.getEmailAddress());
    }

    private void verifyThereIsNoActiveRegistration(String email) throws AccountNotActivatedException {
        Optional<UserActionRequestEntity> activeRegistration = userActionRequestRepository
                .findByEmailAndActionAndState(email, REGISTRATION, UserActionRequestState.ACTIVE);
        if (activeRegistration.isPresent()) {
            throw new AccountNotActivatedException();
        }
    }

    private void verifyThereIsNoCompletedRegistration(String email) throws AccountAlreadyExistsException {
        Optional<UserActionRequestEntity> completedRegistration = userActionRequestRepository
                .findByEmailAndActionAndState(email, REGISTRATION, UserActionRequestState.COMPLETED);
        if (completedRegistration.isPresent()) {
            throw new AccountAlreadyExistsException();
        }
    }


    private AuthToken findUnsubscribeAccessToken(Email email) throws IllegalStateException {
        UserActionRequestEntity userActionRequest = userActionRequestRepository.findByEmailAndAction(email.getEmailAddress(), UNSUBSCRIPTION)
                .orElseThrow(IllegalStateException::new);
        return new AuthToken(userActionRequest.getKey(), UNSUBSCRIPTION);
    }

    private void storeUnconfirmedRegistration(LunchMenuDemand lunchMenuDemand) {
        UnconfirmedLunchDemandEntity unconfirmedLunchDemandEntity = new UnconfirmedLunchDemandEntity(lunchMenuDemand.getEmail(), serializeLunchDemand(lunchMenuDemand));
        unconfirmedLunchDemandRepository.save(unconfirmedLunchDemandEntity);
    }

    private String serializeLunchDemand(LunchMenuDemand lunchMenuDemand) throws IllegalStateException {
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

    private Email verifyToken(AuthToken token) throws InvalidTokenException {
        UserActionRequestEntity activeRequest = userActionRequestRepository.findByKey(token.getAuthKey())
                .filter(UserActionRequestEntity::isActive)
                .orElseThrow(InvalidTokenException::new);
        UserActionRequestEntity completedRequest = activeRequest.complete();
        userActionRequestRepository.save(completedRequest);
        return Email.of(completedRequest.getEmail());
    }

    private LunchMenuDemand getTemporaryRegistration(Email authorizedEmail) throws IllegalStateException {
        String serializedLunchDemand = unconfirmedLunchDemandRepository.findByEmail(authorizedEmail.getEmailAddress())
                .map(UnconfirmedLunchDemandEntity::getSerializedLunchDemand)
                .orElseThrow(IllegalStateException::new);
        return deserializeLunchDemand(serializedLunchDemand);
    }

    private LunchMenuDemand deserializeLunchDemand(String serializedLunchDemand) throws IllegalStateException {
        try {
            return new ObjectMapper().readValue(serializedLunchDemand, LunchMenuDemand.class);
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }
}
