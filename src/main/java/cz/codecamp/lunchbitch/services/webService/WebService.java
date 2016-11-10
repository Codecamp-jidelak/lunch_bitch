package cz.codecamp.lunchbitch.services.webService;

import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.Restaurant;

import java.util.List;

public interface WebService {
    public List<Restaurant> getFoundRestaurants();
    public List<Restaurant> getSelectedRestaurants();
    public void saveSearchResult(LunchMenuDemand result);
    public void addSelectedRestaurantIDs(List<String> selectedRestaurantIDs);
    public void setLunchMenuDemandEmail(String email);
    public LunchMenuDemand getLunchMenuDemandPreferences();
    public String getEmail();
}
