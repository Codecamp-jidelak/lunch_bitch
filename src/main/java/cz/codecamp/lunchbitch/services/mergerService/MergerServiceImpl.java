package cz.codecamp.lunchbitch.services.mergerService;

import cz.codecamp.lunchbitch.models.LunchMenu;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.Restaurant;
import cz.codecamp.lunchbitch.services.emailService.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;

@Service
public class MergerServiceImpl implements MergerService {

    private final EmailService emailService;

    @Autowired
    public MergerServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }


    @Override
    public List<LunchMenuDemand> mergeLunchMenusWithRestaurants(Map<String, LunchMenu> lunchMenuMap, List<LunchMenuDemand> demands) throws MessagingException {

        for(LunchMenuDemand demand: demands){

            for(Restaurant restaurant: demand.getRestaurants()){

                restaurant.setLunchmenu(lunchMenuMap.get(restaurant.getId()));

            }

        }
        emailService.sendEmailsToSubscribers(demands);
        return demands;
    }
}
