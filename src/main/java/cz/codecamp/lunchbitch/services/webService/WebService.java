package cz.codecamp.lunchbitch.services.webService;

import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.Restaurant;
import cz.codecamp.lunchbitch.webPageMappers.SearchForm;

import java.util.List;

public interface WebService {
    List<Restaurant> getFoundRestaurants();
    List<Restaurant> getSelectedRestaurants();
    boolean saveSearchResult(SearchForm searchForm);
    void addSelectedRestaurantIDs(List<String> selectedRestaurantIDs);
    void updateSelectedRestaurantIDs(List<String> selectedRestaurantIDs);
    void setLunchMenuDemandEmail(String email);
    String getEmail();
    boolean isEmptySelectedRestaurantsList();
    void saveLunchMenuPreferences();
    void unsubscribeMenuPreferences(String email);
}
