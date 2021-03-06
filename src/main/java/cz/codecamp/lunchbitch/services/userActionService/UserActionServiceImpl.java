package cz.codecamp.lunchbitch.services.userActionService;

import cz.codecamp.lunchbitch.models.AuthToken;
import cz.codecamp.lunchbitch.models.Email;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.exceptions.AccountAlreadyExistsException;
import cz.codecamp.lunchbitch.models.exceptions.AccountDoesNotExistException;
import cz.codecamp.lunchbitch.models.exceptions.AccountNotActivatedException;
import cz.codecamp.lunchbitch.models.exceptions.InvalidTokenException;
import cz.codecamp.lunchbitch.services.authorizationService.AuthorizationService;
import cz.codecamp.lunchbitch.services.emailService.EmailService;
import cz.codecamp.lunchbitch.services.triggerAndStorageService.LunchMenuDemandStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;

@Service
public class UserActionServiceImpl implements UserActionService {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private LunchMenuDemandStorageService storageService;

    @Autowired
    private EmailService emailService;

    @Override
    public void submitRegistration(LunchMenuDemand menu) throws AccountAlreadyExistsException, AccountNotActivatedException, IllegalStateException {
        AuthToken authToken = authorizationService.requestRegistrationConfirmation(menu);
        sendUserActionRequestEmail(Email.of(menu.getEmail()), authToken);
    }

    @Override
    public Email activateRegistration(AuthToken token) throws InvalidTokenException, IllegalStateException {
        LunchMenuDemand lunchMenuDemand = authorizationService.authorizeRegistration(token);
        storageService.saveLunchDemand(lunchMenuDemand);
        return Email.of(lunchMenuDemand.getEmail());
    }

    @Override
    public void submitUpdateOrDeleteRequest(Email email) throws AccountNotActivatedException, AccountDoesNotExistException {
        AuthToken authToken = authorizationService.requestChangeAccess(email);
        sendUserActionRequestEmail(email, authToken);
    }

    private void sendUserActionRequestEmail(Email email, AuthToken authToken) {
        try {
            emailService.sendUserActionRequestEmail(authToken, email);
        } catch (MessagingException me) {
            throw new IllegalStateException();
        }
    }

    @Override
    public LunchMenuDemand authorizeUpdateDemand(AuthToken token) throws InvalidTokenException {
        Email email = authorizationService.authorizeChange(token);
        return storageService.getLunchMenuDemand(email.getEmailAddress());
    }

    @Override
    public void storeUpdatedDemand(LunchMenuDemand demand) {
        storageService.deleteLunchMenuDemand(demand.getEmail());
        storageService.saveLunchDemand(demand);
    }

    @Override
    public Email authorizeDeleteAccount(AuthToken token) throws InvalidTokenException {
        return authorizationService.authorizeUnsubscription(token);
    }

    @Override
    @Transactional
    public void deleteUserAccount(Email email) {
        storageService.deleteLunchMenuDemand(email.getEmailAddress());
        authorizationService.removeAllUserActionRequestRecordsForUnsubscribedAccount(email);
    }
}
