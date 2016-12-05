package cz.codecamp.lunchbitch.services.emailService;

import cz.codecamp.lunchbitch.models.AuthToken;
import cz.codecamp.lunchbitch.models.Email;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;

@Service
public interface EmailService {

    void sendDailyLunchMenusToSubscribers(List<LunchMenuDemand> lunchMenuDemandList, Map<String, AuthToken> unsubscribeTokensByEmails) throws MessagingException;

    void sendUserActionRequestEmail(AuthToken token, Email usersEmail) throws MessagingException;

}
