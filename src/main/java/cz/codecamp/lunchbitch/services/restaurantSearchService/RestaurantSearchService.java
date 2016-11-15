package cz.codecamp.lunchbitch.services.restaurantSearchService;

import cz.codecamp.lunchbitch.models.Location;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface RestaurantSearchService  {

    /**
     * Search for restaurants by keyword
     * @param keyword to search
     * @return LunchMenuDemand of results
     * @throws IOException
     */
    LunchMenuDemand searchForRestaurants(String keyword) throws IOException;

    LunchMenuDemand searchForRestaurants(Location location) throws IOException;
}
