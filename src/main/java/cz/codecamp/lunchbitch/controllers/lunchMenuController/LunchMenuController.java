package cz.codecamp.lunchbitch.controllers.lunchMenuController;


import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.Restaurant;
import cz.codecamp.lunchbitch.services.lunchMenuService.LunchMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping(value = "/lunch")
public class LunchMenuController {

    private final LunchMenuService lunchMenuService;

    @Autowired
    public LunchMenuController(LunchMenuService lunchMenuService) {
        this.lunchMenuService = lunchMenuService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<LunchMenuDemand> saveLunchMenuPreferences() throws IOException {
        List<String> list = new ArrayList<>();
        List<LunchMenuDemand> demands = new ArrayList<>();
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId("16506954");
        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId("16506385");
        Restaurant restaurant3 = new Restaurant();
        restaurant3.setId("16511008");
        list.add("16506954");
        list.add("16506385");
        list.add("16511008");

        LunchMenuDemand demand = new LunchMenuDemand();
        demand.addRestaurant(restaurant1);
        demand.addRestaurant(restaurant2);
        demand.addRestaurant(restaurant3);

        demands.add(demand);
        return lunchMenuService.lunchMenuDownload(list, demands);
    }
}
