package cz.codecamp.lunchbitch.services.triggerAndStorageService;

import cz.codecamp.lunchbitch.models.*;
import cz.codecamp.lunchbitch.models.exceptions.InvalidTokenException;
import cz.codecamp.lunchbitch.services.authorizationService.crypto.AuthKeyProvider;
import cz.codecamp.lunchbitch.services.emailService.EmailService;
import cz.codecamp.lunchbitch.models.exceptions.AccountNotActivatedException;
import cz.codecamp.lunchbitch.services.userActionService.UserActionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LunchMenuTriggerServiceImplTest {


    @Autowired
    LunchMenuSendingTrigger lunchMenuSendingTrigger;

    @Autowired
    UserActionService userActionService;

    //no need for verifying anything, just preventing sending of emails.
    @MockBean
    EmailService emailService;

    @MockBean
    AuthKeyProvider authKeyProvider;


    @Test
    public void stuff() throws MessagingException, AccountNotActivatedException, InvalidTokenException {
        given(authKeyProvider.generateNextRandomAuthKey()).willReturn("AAA").willReturn("BBB").willReturn("CCC").willReturn("DDD").willReturn("EEE").willReturn("FFF");

        LunchMenuDemand lunchMenuDemand = new LunchMenuDemand();
        lunchMenuDemand.setEmail("peternikodemjr@gmail.com");

        Restaurant restaurant = new Restaurant();
        restaurant.setId("16521093");
        restaurant.setName("kozlovna1");
        restaurant.setLocation(new Location());

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId("16508973");
        restaurant2.setName("kozlovna2");
        restaurant2.setLocation(new Location());

        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId("16508975");
        restaurant3.setName("kozlovna3");
        restaurant3.setLocation(new Location());
        lunchMenuDemand.setRestaurants(Arrays.asList(restaurant, restaurant2));

        userActionService.submitRegistration(lunchMenuDemand);

        userActionService.activateRegistration(new AuthToken("AAA", UserAction.REGISTRATION));

        lunchMenuSendingTrigger.triggerSending("lal");


    }
}