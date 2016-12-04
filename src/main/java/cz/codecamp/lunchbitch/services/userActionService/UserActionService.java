package cz.codecamp.lunchbitch.services.userActionService;

import cz.codecamp.lunchbitch.models.AuthToken;
import cz.codecamp.lunchbitch.models.Email;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.exceptions.AccountAlreadyExistsException;
import cz.codecamp.lunchbitch.models.exceptions.AccountDoesNotExistException;
import cz.codecamp.lunchbitch.models.exceptions.InvalidTokenException;

import javax.mail.MessagingException;

public interface UserActionService {

    void submitRegistration(LunchMenuDemand menu) throws AccountAlreadyExistsException, AccountNotActivatedException, IllegalStateException, MessagingException;

    Email activateRegistration(AuthToken token) throws InvalidTokenException, IllegalStateException;

    void submitUpdateOrDeleteRequest(Email email) throws AccountNotActivatedException, AccountDoesNotExistException, MessagingException;

    LunchMenuDemand authorizeUpdateDemand(AuthToken token) throws InvalidTokenException;

    void storeUpdatedDemand(LunchMenuDemand demand);

    Email authorizeDeleteAccount(AuthToken token) throws InvalidTokenException;

    void deleteUserAccount(Email email);

}
