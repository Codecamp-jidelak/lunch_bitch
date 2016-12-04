package cz.codecamp.lunchbitch.services.webService;

import cz.codecamp.lunchbitch.models.Location;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.Restaurant;
import cz.codecamp.lunchbitch.models.SubmitState;
import cz.codecamp.lunchbitch.models.exceptions.AccountAlreadyExistsException;
import cz.codecamp.lunchbitch.services.geocodingService.GeocodingService;
import cz.codecamp.lunchbitch.services.lunchMenuDemandService.LunchMenuDemandService;
import cz.codecamp.lunchbitch.services.restaurantSearchService.RestaurantSearchService;
import cz.codecamp.lunchbitch.services.userActionService.AccountNotActivatedException;
import cz.codecamp.lunchbitch.services.userActionService.UserActionService;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Scope(value = "session", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class WebServiceImpl implements WebService {

    private LunchMenuDemand searchResults;
    private Set<Restaurant> selectedRestaurants;
    private String userEmail;

    private RestaurantSearchService restaurantSearchService;
    private GeocodingService geocodingService;
    private LunchMenuDemandService lunchMenuDemandService;
    private UserActionService userActionService;
    private final Logger logger;

    @Autowired
    public WebServiceImpl(LunchMenuDemand lunchMenuDemand, RestaurantSearchService restaurantSearchService, GeocodingService geocodingService,
                          LunchMenuDemandService lunchMenuDemandService, UserActionService userActionService, Logger logger) {
        this.searchResults = lunchMenuDemand;
        this.restaurantSearchService = restaurantSearchService;
        this.geocodingService = geocodingService;
        this.lunchMenuDemandService = lunchMenuDemandService;
        this.userActionService = userActionService;
        this.logger = logger;
        selectedRestaurants = new HashSet<>();
    }

    @Override
    public List<Restaurant> getFoundRestaurants() {
        return searchResults.getRestaurants();
    }

    @Override
    public List<Restaurant> getSelectedRestaurants() {
        return new ArrayList<>(selectedRestaurants);
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
        selectedRestaurants.addAll(newRestaurants);
    }

    @Override
    public void updateSelectedRestaurantIDs(List<String> selectedRestaurantIDs) {
        selectedRestaurants = matchRestaurantsByIDs(selectedRestaurantIDs, new ArrayList<>(selectedRestaurants));
    }

    @Override
    public String getEmail() {
        return userEmail;
    }

    @Override
    public boolean isEmptySelectedRestaurantsList() {
        return selectedRestaurants.isEmpty();
    }

    @Override
    public SubmitState submitLunchMenuPreferences(String email) {
        // TODO: remove when UserActionService is implemented
        lunchMenuDemandService.saveLunchMenuPreferences(preparePreferences(email));

        SubmitState submitState;

        try {
            userActionService.submitRegistration(preparePreferences(email));
            submitState = SubmitState.SUCCESS;
        } catch (AccountAlreadyExistsException e) {
            logger.log(Level.WARNING, "submitLMP - account already exist for e-mail: " + email, e);
            submitState = SubmitState.ALREADYEXISTS;
        } catch (AccountNotActivatedException e) {
            logger.log(Level.WARNING, "submitLMP - account not activated for e-mail: " + email, e);
            submitState = SubmitState.NOTACTIVATED;
        } catch (IllegalStateException e) {
            logger.log(Level.WARNING, "submitLMP - IllegalStateException for e-mail: " + email, e);
            submitState = SubmitState.ILLEGAL;
        }

        preparePreferences(email);
        deleteUserActivity();
        return submitState;
    }

    @Override
    public void saveLunchMenuPreferences() {
        userActionService.storeUpdatedDemand(preparePreferences(userEmail));
        deleteUserActivity();
    }

    @Override
    public void loadUsersSettings(LunchMenuDemand lunchMenuDemand) {
        userEmail = lunchMenuDemand.getEmail();
        selectedRestaurants.clear();
        selectedRestaurants.addAll(lunchMenuDemand.getRestaurants());
    }

    @Override
    public void clearCurrentProgress() {
        deleteUserActivity();
    }

    private void deleteUserActivity() {
        selectedRestaurants.clear();
        userEmail = null;
    }

    private LunchMenuDemand preparePreferences(String email) {
        LunchMenuDemand rest = new LunchMenuDemand();
        rest.setEmail(email);
        rest.setRestaurants(new ArrayList<>(selectedRestaurants));
        return rest;
    }
}
