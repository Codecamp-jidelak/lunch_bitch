package cz.codecamp.lunchbitch.services.webService;

import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.Restaurant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WebServiceImpl implements WebService {

    private LunchMenuDemand searchResults;
    private LunchMenuDemand selectedRestaurants;
	private Set<Restaurant> expectedRestaurants;

    public WebServiceImpl(LunchMenuDemand lunchMenuDemand, LunchMenuDemand selectedRestaurants) {
        this.searchResults = lunchMenuDemand;
	    this.selectedRestaurants = selectedRestaurants;
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
    public void saveSearchResult(LunchMenuDemand result) {
        searchResults = result;
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
	public LunchMenuDemand getLunchMenuDemandPreferences() {
		selectedRestaurants.setRestaurants(new ArrayList<>(expectedRestaurants));
		expectedRestaurants.clear();
		return selectedRestaurants;
	}

	@Override
	public String getEmail() {
		return selectedRestaurants.getEmail();
	}

	public void setSelectedRestaurants(LunchMenuDemand selectedRestaurants) {
		this.selectedRestaurants = selectedRestaurants;
	}
}
