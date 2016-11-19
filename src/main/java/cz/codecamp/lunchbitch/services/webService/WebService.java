package cz.codecamp.lunchbitch.services.webService;

import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.Restaurant;

import java.util.List;

public interface WebService {
    List<Restaurant> getFoundRestaurants();
    List<Restaurant> getSelectedRestaurants();
    void saveSearchResult(LunchMenuDemand result);
    void addSelectedRestaurantIDs(List<String> selectedRestaurantIDs);
    void updateSelectedRestaurantIDs(List<String> selectedRestaurantIDs);
    void setLunchMenuDemandEmail(String email);
    LunchMenuDemand getLunchMenuDemandPreferences();
    String getEmail();
    boolean isEmptySelectedRestaurantsList();
}
