package cz.codecamp.lunchbitch.services.emailService;

import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class EmailServiceImpl implements EmailService {

    private final Message message;

    private final TemplateEngine templateEngine;

    private Context context = new Context(new Locale("cs", "cz"));

    @Autowired
    public EmailServiceImpl(Message message, TemplateEngine templateEngine) {
        this.message = message;
        this.templateEngine = templateEngine;
    }


    public void sendEmailsToSubscribers(List<LunchMenuDemand> lunchMenuDemandList) throws MessagingException {
        for(LunchMenuDemand lunchMenu : lunchMenuDemandList){
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(lunchMenu.getEmail()));
            message.setContent(buildLunchMenuList(lunchMenu.getRestaurants()), "text/html; charset=utf-8");
            Transport.send(message);
        }
    }

    private String buildLunchMenuList(List<Restaurant> restaurants) throws MessagingException {
        context.setVariable("name", "Pošli jídelák ;-)");
        context.setVariable("date", new Date());
        context.setVariable("restaurants", restaurants);
        return templateEngine.process("html/email-template", context);
    }

}
