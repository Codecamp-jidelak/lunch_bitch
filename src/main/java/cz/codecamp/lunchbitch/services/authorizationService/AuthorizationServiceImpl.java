package cz.codecamp.lunchbitch.services.authorizationService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.codecamp.lunchbitch.entities.UnconfirmedLunchDemandEntity;
import cz.codecamp.lunchbitch.entities.UserActionRequestEntity;
import cz.codecamp.lunchbitch.models.AuthToken;
import cz.codecamp.lunchbitch.models.Email;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.UserAction;
import cz.codecamp.lunchbitch.models.exceptions.InvalidAuthKeyException;
import cz.codecamp.lunchbitch.repositories.UnconfirmedLunchDemandRepository;
import cz.codecamp.lunchbitch.repositories.UserActionRequestRepository;
import cz.codecamp.lunchbitch.services.authorizationService.crypto.AuthKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static cz.codecamp.lunchbitch.models.UserAction.UPDATE;
import static cz.codecamp.lunchbitch.models.UserAction.REGISTRATION;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private UserActionRequestRepository userActionRequestRepository;

    @Autowired
    private UnconfirmedLunchDemandRepository unconfirmedLunchDemandRepository;


    @Override
    public AuthToken requestRegistrationConfirmation(LunchMenuDemand lunchMenuDemand) {
        storeUnconfirmedRegistration(lunchMenuDemand);
        return generateAndSaveAuthToken(lunchMenuDemand.getEmail(), REGISTRATION);
    }

    @Override
    public AuthToken requestChangeAccess(Email email) {
        return generateAndSaveAuthToken(email.getEmailAdress(), UPDATE);
    }

    @Override
    public AuthToken requestUnsubscribeAccess(Email email) {
        return null;
    }

    @Override
    public LunchMenuDemand authorizeRegistration(AuthToken registrationToken) {
        Email authorizedEmail = verifyToken(registrationToken);
        return getTemporaryRegistration(authorizedEmail);
    }

    @Override
    public Email authorizeChange(AuthToken changeToken) {
        return verifyToken(changeToken);
    }

    @Override
    public Email authorizeUnsubscription(AuthToken unsubscribeToken) {
        return null;
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
        String authKey = new AuthKeyGenerator().generateNextRandomAuthKey();
        userActionRequestRepository.save(UserActionRequestEntity.activateNew(email, authKey, userAction));
        return AuthToken.of(authKey, userAction);
    }

    private Email verifyToken(AuthToken registrationToken) {
        UserActionRequestEntity activeRequest = userActionRequestRepository.findByKey(registrationToken.getAuthKey())
                .filter(UserActionRequestEntity::isActive)
                .orElseThrow(InvalidAuthKeyException::new);
        UserActionRequestEntity completedRequest = activeRequest.complete();
        userActionRequestRepository.save(completedRequest);
        return Email.of(completedRequest.getEmail());
    }

    private LunchMenuDemand getTemporaryRegistration(Email authorizedEmail) {
        String serializedLunchDemand = unconfirmedLunchDemandRepository.findByEmail(authorizedEmail.getEmailAdress())
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
