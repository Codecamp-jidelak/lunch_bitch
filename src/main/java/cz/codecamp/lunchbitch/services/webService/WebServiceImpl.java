package cz.codecamp.lunchbitch.services.webService;

import cz.codecamp.lunchbitch.models.Location;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.Restaurant;
import cz.codecamp.lunchbitch.services.geocodingService.GeocodingService;
import cz.codecamp.lunchbitch.services.lunchMenuDemandService.LunchMenuDemandService;
import cz.codecamp.lunchbitch.services.restaurantSearchService.RestaurantSearchService;
import cz.codecamp.lunchbitch.webPageMappers.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Scope(value = "session", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class WebServiceImpl implements WebService {

    private LunchMenuDemand searchResults;
    private LunchMenuDemand selectedRestaurants;
    private Set<Restaurant> expectedRestaurants;
    private String userHash;

    private RestaurantSearchService restaurantSearchService;
    private GeocodingService geocodingService;
    private LunchMenuDemandService lunchMenuDemandService;

    @Autowired
    public WebServiceImpl(LunchMenuDemand lunchMenuDemand, LunchMenuDemand selectedRestaurants,
                          RestaurantSearchService restaurantSearchService, GeocodingService geocodingService, LunchMenuDemandService lunchMenuDemandService) {
        this.searchResults = lunchMenuDemand;
        this.selectedRestaurants = selectedRestaurants;
        this.restaurantSearchService = restaurantSearchService;
        this.geocodingService = geocodingService;
        this.lunchMenuDemandService = lunchMenuDemandService;
        expectedRestaurants = new HashSet<>();
    }

    @Override
    public List<Restaurant> getFoundRestaurants() {
        return searchResults.getRestaurants();
    }

    @Override
    public List<Restaurant> getSelectedRestaurants() {
        return new ArrayList<>(expectedRestaurants);
    }

    @Override
    public boolean saveSearchResult(SearchForm searchForm) {
        String formTextInput = searchForm.getKeyword();
        String type = searchForm.getType();

        if (formTextInput != null && !formTextInput.trim().isEmpty()) {

            try {
                if ("keyword".equals(type)) {
                    searchResults = restaurantSearchService.searchForRestaurants(formTextInput);
                } else if ("address".equals(type)) {
                    Location location = geocodingService.getCoordinates(formTextInput);
                    searchResults = restaurantSearchService.searchForRestaurants(location);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    private Set<Restaurant> matchRestaurantsByIDs(List<String> selectedRestaurantIDs, List<Restaurant> sourceRestaurants) {
        Set<Restaurant> matchedRestaurants = new HashSet<>();
        for(String id : selectedRestaurantIDs) {
            matchedRestaurants.addAll(sourceRestaurants.stream().filter(restaurant -> restaurant.getId().equals(id)).collect(Collectors.toList()));
        }
        return matchedRestaurants;
    }

    @Override
    public void addSelectedRestaurantIDs(List<String> selectedRestaurantIDs) {
        Set<Restaurant> newRestaurants = matchRestaurantsByIDs(selectedRestaurantIDs, searchResults.getRestaurants());
        expectedRestaurants.addAll(newRestaurants);
    }

    @Override
    public void updateSelectedRestaurantIDs(List<String> selectedRestaurantIDs) {
        expectedRestaurants = matchRestaurantsByIDs(selectedRestaurantIDs, new ArrayList<>(expectedRestaurants));
    }

    @Override
    public void setLunchMenuDemandEmail(String email) {
        selectedRestaurants.setEmail(email);
    }

    @Override
    public String getEmail() {
        return selectedRestaurants.getEmail();
    }

    @Override
    public boolean isEmptySelectedRestaurantsList() {
        return expectedRestaurants.isEmpty();
    }

    @Override
    public void saveLunchMenuPreferences() {
        selectedRestaurants.setRestaurants(new ArrayList<>(expectedRestaurants));
        expectedRestaurants.clear();
        lunchMenuDemandService.saveLunchMenuPreferences(selectedRestaurants);
    }

    @Override
    public void unsubscribeMenuPreferences(String email) {
        lunchMenuDemandService.unsubscribeMenuPreferences(email);
    }
}
