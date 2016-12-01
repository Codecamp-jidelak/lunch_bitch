package cz.codecamp.lunchbitch.services.authorizationService;

import cz.codecamp.lunchbitch.models.AuthToken;
import cz.codecamp.lunchbitch.models.Email;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;

public interface AuthorizationService {

    AuthToken requestRegistrationConfirmation(LunchMenuDemand lunchMenuDemand);

    AuthToken requestChangeAccess(Email email);

    AuthToken requestUnsubscribeAccess(Email email);

    LunchMenuDemand authorizeRegistration(AuthToken registationToken);

    Email authorizeChange(AuthToken changeToken);

    Email authorizeUnsubscription(AuthToken unsubscribeToken);

}
