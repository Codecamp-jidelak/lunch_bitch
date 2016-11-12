package cz.codecamp.lunchbitch.services.webService;

import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.Restaurant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	@Override
	public void addSelectedRestaurantIDs(List<String> selectedRestaurantIDs) {
		List<Restaurant> foundRestaurants = searchResults.getRestaurants();
		for (String id : selectedRestaurantIDs) {
			for (Restaurant restaurant : foundRestaurants) {
				if (restaurant.getId().equals(id)) {
					expectedRestaurants.add(restaurant);
				}
			}
		}
	}

	@Override
	public void updateSelectedRestaurantIDs(List<String> selectedRestaurantIDs) {
		expectedRestaurants.clear();
		addSelectedRestaurantIDs(selectedRestaurantIDs);
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
