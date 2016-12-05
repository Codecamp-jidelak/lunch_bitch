package cz.codecamp.lunchbitch.services.mergerService;


import cz.codecamp.lunchbitch.models.AuthToken;
import cz.codecamp.lunchbitch.models.LunchMenu;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;

@Service
public interface MergerService {

    List<LunchMenuDemand> mergeLunchMenusWithRestaurants(Map<String, LunchMenu> lunchMenuMap, List<LunchMenuDemand> demands, Map<String, AuthToken> unsubscribeTokensByEmails) throws MessagingException;

}
