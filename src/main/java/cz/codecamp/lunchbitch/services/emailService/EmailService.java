package cz.codecamp.lunchbitch.services.emailService;

import cz.codecamp.lunchbitch.models.AuthToken;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
public interface EmailService {

    void sendDailyLunchMenusToSubscribers(List<LunchMenuDemand> lunchMenuDemandList) throws MessagingException;



}
