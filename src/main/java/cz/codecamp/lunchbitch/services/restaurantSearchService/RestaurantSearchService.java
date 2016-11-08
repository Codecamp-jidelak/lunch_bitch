package cz.codecamp.lunchbitch.services.restaurantSearchService;

import cz.codecamp.lunchbitch.models.DataModel;

import java.io.IOException;


public interface RestaurantSearchService  {

    DataModel searchForRestaurants(String keyword) throws IOException;
}
