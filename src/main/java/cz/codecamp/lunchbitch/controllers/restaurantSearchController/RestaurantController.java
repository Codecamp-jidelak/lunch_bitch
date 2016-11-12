package cz.codecamp.lunchbitch.controllers.restaurantSearchController;

import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.services.restaurantSearchService.RestaurantSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@SuppressWarnings("unused")
@RestController
@RequestMapping(value = "/search")
public class RestaurantController {

    private final RestaurantSearchService service;

    @Autowired
    public RestaurantController(RestaurantSearchService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    LunchMenuDemand searchRestaurant(@RequestParam String keyword) throws IOException {
        return service.searchForRestaurants(keyword);
    }

}
