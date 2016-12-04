package cz.codecamp.lunchbitch.services.emailService;

import cz.codecamp.lunchbitch.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class EmailServiceImpl implements EmailService {

    private final Message lunchMessage;

    private final Message activationMessage;

    private final TemplateEngine templateEngine;

    private Context context = new Context(new Locale("cs", "cz"));

    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd. MM. YYYY", new Locale("cs", "cz"));

    @Value("${application.link}")
    private String applicationLink;

    @Autowired
    public EmailServiceImpl(@Qualifier("lunch") Message message, @Qualifier("activation") Message activationMessage, TemplateEngine templateEngine) {
        this.lunchMessage = message;
        this.activationMessage = activationMessage;
        this.templateEngine = templateEngine;
    }


    @Override
    public void sendDailyLunchMenusToSubscribers(List<LunchMenuDemand> lunchMenuDemandList) throws MessagingException {
        for (LunchMenuDemand lunchMenu : lunchMenuDemandList) {
            lunchMessage.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(lunchMenu.getEmail()));
            lunchMessage.setContent(buildLunchMenuList(lunchMenu.getRestaurants()), "text/html; charset=utf-8");
            Transport.send(lunchMessage);
        }
    }

    @Override
    public void sendUserActionRequestEmail(AuthToken token, Email usersEmail) throws MessagingException {
        activationMessage.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(usersEmail.getEmailAdress()));
        activationMessage.setContent(buildUserActionRequestEmail(token, usersEmail), "text/html; charset=utf-8");
        Transport.send(activationMessage);
    }

    private String buildLunchMenuList(List<Restaurant> restaurants) throws MessagingException {
        List<Restaurant> restaurantsWithLunchMenu = new ArrayList<>();
        List<Restaurant> restaurantsWithoutLunchMenu = new ArrayList<>();

        for(Restaurant restaurant : restaurants){

            if(!restaurant.hasLunchMenu() || !restaurant.getLunchmenu().hasDishes()){
                restaurantsWithoutLunchMenu.add(restaurant);
            }else{
                restaurantsWithLunchMenu.add(restaurant);
            }
        }

        context.setVariable("name", "Pošli jídelák ;-)");
        context.setVariable("date", new Date());
        context.setVariable("restaurantsWithLunchMenu", restaurantsWithLunchMenu);
        context.setVariable("restaurantsWithoutLunchMenu", restaurantsWithoutLunchMenu);
        return templateEngine.process("html/lunch-menu-template", context);
    }

    private String buildUserActionRequestEmail(AuthToken token, Email usersEmail){

        context.setVariable("email", usersEmail.getEmailAdress());

        String link = "";

        switch (token.getAction()){
            case REGISTRATION:
                link = "/activate/" + token.getAuthKey();
                break;
            case UPDATE:
                link = "/settings/" + token.getAuthKey();
                break;
            case UNSUBSCRIPTION:
                link = "/unsubscribe/" + token.getAuthKey();
                break;
        }
        context.setVariable("activationLink", link);
        context.setVariable("applicationLink", applicationLink);
        context.setVariable("action", token.getAction());
        context.setVariable("date", LocalDate.now().format(timeFormatter));
        return templateEngine.process("html/user-action-template", context);
    }


}
