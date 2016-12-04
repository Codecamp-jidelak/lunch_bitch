package cz.codecamp.lunchbitch.services.userActionService;

import cz.codecamp.lunchbitch.models.AuthToken;
import cz.codecamp.lunchbitch.models.Email;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.exceptions.AccountAlreadyExistsException;
import cz.codecamp.lunchbitch.models.exceptions.AccountDoesNotExistException;
import cz.codecamp.lunchbitch.models.exceptions.InvalidTokenException;
import cz.codecamp.lunchbitch.services.authorizationService.AuthorizationService;
import cz.codecamp.lunchbitch.services.emailService.EmailService;
import cz.codecamp.lunchbitch.services.lunchMenuDemandService.LunchMenuDemandService;
import cz.codecamp.lunchbitch.services.triggerAndStorageService.LunchMenuDemandStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void submitRegistration(LunchMenuDemand menu) throws AccountAlreadyExistsException, AccountNotActivatedException, IllegalStateException, MessagingException {
        AuthToken authToken = authorizationService.requestRegistrationConfirmation(menu);
        emailService.sendUserActionRequestEmail(authToken, Email.of(menu.getEmail()));
    }

    @Override
    public Email activateRegistration(AuthToken token) throws InvalidTokenException, IllegalStateException {
        LunchMenuDemand lunchMenuDemand = authorizationService.authorizeRegistration(token);
        storageService.saveLunchDemand(lunchMenuDemand);
        return Email.of(lunchMenuDemand.getEmail());
    }

    @Override
    public void submitUpdateOrDeleteRequest(Email email) throws AccountNotActivatedException, AccountDoesNotExistException, MessagingException {
        AuthToken authToken = authorizationService.requestChangeAccess(email);
        emailService.sendUserActionRequestEmail(authToken, email);
    }

    @Override
    public LunchMenuDemand authorizeUpdateDemand(AuthToken token) throws InvalidTokenException {
        Email email = authorizationService.authorizeChange(token);
        return storageService.getLunchMenuDemand(email.getEmailAdress());
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
    public void deleteUserAccount(Email email) {
        storageService.deleteLunchMenuDemand(email.getEmailAdress());
    }
}
