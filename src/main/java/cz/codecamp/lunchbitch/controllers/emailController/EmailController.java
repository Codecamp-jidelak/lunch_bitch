package cz.codecamp.lunchbitch.controllers.emailController;

import cz.codecamp.lunchbitch.models.Location;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.Restaurant;
import cz.codecamp.lunchbitch.services.emailService.EmailService;
import cz.codecamp.lunchbitch.services.lunchMenuService.LunchMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping(value = "/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private LunchMenuService lunchMenuService;

    @RequestMapping(method = RequestMethod.GET)
    public void sendUserActionEmail() throws MessagingException, IOException {

        List<String> list = new ArrayList<>();
        List<LunchMenuDemand> demands = new ArrayList<>();
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId("16506954");
        restaurant1.setName("Kozlovna");
        restaurant1.setLocation(new Location().setAddress("Dlážděná 7, Nové Město, Praha 1"));
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId("16511008");
        restaurant2.setName("Irish pub");
        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId("16511008");
        restaurant3.setName("Pizzeria Novato");
        list.add("16506954");
        list.add("16506385");
        list.add("16511008");

        LunchMenuDemand demand = new LunchMenuDemand();
        demand.setEmail("hornych.h@gmail.com");
        demand.addRestaurant(restaurant1);
        demand.addRestaurant(restaurant2);
        demand.addRestaurant(restaurant3);

        demands.add(demand);


        emailService.sendDailyLunchMenusToSubscribers(lunchMenuService.lunchMenuDownload(list, demands));
    }

}
