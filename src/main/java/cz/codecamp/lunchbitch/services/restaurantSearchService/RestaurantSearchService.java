package cz.codecamp.lunchbitch.services.restaurantSearchService;

import cz.codecamp.lunchbitch.models.DataModel;
import java.io.IOException;


public interface RestaurantSearchService  {

    /**
     * Search for restaurants by keyword
     * @param keyword to search
     * @return DataModel of results
     * @throws IOException
     */
    DataModel searchForRestaurants(String keyword) throws IOException;
}
