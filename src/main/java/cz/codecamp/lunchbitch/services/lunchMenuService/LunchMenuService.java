package cz.codecamp.lunchbitch.services.lunchMenuService;


import cz.codecamp.lunchbitch.models.AuthToken;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface LunchMenuService {

    /**
     * Download LunchMenuDetail for all restaurants by it's ID
     *
     * @param restaurants id's
     * @param demands     to proceed
     */
    List<LunchMenuDemand> lunchMenuDownload(List<String> restaurants, List<LunchMenuDemand> demands, Map<String, AuthToken> unsubscribeTokensByEmails) throws IOException, MessagingException;

}
