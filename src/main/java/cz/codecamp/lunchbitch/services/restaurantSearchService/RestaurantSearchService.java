package cz.codecamp.lunchbitch.services.restaurantSearchService;

import cz.codecamp.lunchbitch.models.LunchMenuDemand;

import java.io.IOException;


public interface RestaurantSearchService  {

    /**
     * Search for restaurants by keyword
     * @param keyword to search
     * @return LunchMenuDemand of results
     * @throws IOException
     */
    LunchMenuDemand searchForRestaurants(String keyword) throws IOException;
}
