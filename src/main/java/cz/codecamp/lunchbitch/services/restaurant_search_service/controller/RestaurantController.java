package cz.codecamp.lunchbitch.services.restaurant_search_service.controller;

import model.DataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import cz.codecamp.lunchbitch.services.restaurant_search_service.service.RestaurantService;

import java.io.IOException;

@RestController
@RequestMapping(value = "/")
public class RestaurantController {

    private final RestaurantService service;

    @Autowired
    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody DataModel searchRestaurant(@RequestParam String keyword) throws IOException {
        return service.searchForRestaurants(keyword);
    }

}
