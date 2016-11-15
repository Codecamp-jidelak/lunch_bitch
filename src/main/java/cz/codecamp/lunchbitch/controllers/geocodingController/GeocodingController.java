package cz.codecamp.lunchbitch.controllers.geocodingController;

import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.services.geocodingService.GeocodingService;
import cz.codecamp.lunchbitch.services.restaurantSearchService.RestaurantSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@SuppressWarnings("unused")
@RestController
@RequestMapping(value = "/geocode")
public class GeocodingController {

    private final GeocodingService geocodingService;

    private final RestaurantSearchService restaurantSearchService;

    @Autowired
    public GeocodingController(RestaurantSearchService restaurantSearchService, GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
        this.restaurantSearchService = restaurantSearchService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    LunchMenuDemand geocode(@RequestParam String address) throws IOException {
        return restaurantSearchService.searchForRestaurants(geocodingService.getCoordinates(address));
    }

}
