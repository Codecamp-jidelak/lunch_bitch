package cz.codecamp.lunchbitch.controllers.restaurantSearchController;

import cz.codecamp.lunchbitch.models.DataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import cz.codecamp.lunchbitch.services.restaurantSearchService.RestaurantSearch;

import java.io.IOException;

@RestController
@RequestMapping(value = "/")
public class RestaurantController {

    private final RestaurantSearch service;

    @Autowired
    public RestaurantController(RestaurantSearch service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody DataModel searchRestaurant(@RequestParam String keyword) throws IOException {
        return service.searchForRestaurants(keyword);
    }

}
