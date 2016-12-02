package cz.codecamp.lunchbitch.controllers.emailController;

import cz.codecamp.lunchbitch.models.AuthToken;
import cz.codecamp.lunchbitch.models.Email;
import cz.codecamp.lunchbitch.models.UserAction;
import cz.codecamp.lunchbitch.services.emailService.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@SuppressWarnings("unused")
@RestController
@RequestMapping(value = "/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping(method = RequestMethod.GET)
    public void sendUserActionEmail() throws MessagingException {
        emailService.sendUserActionRequestEmail(new AuthToken("dfadfs565adf8as", UserAction.UPDATE), new Email("hornych.h@gmail.com"));
    }

}
