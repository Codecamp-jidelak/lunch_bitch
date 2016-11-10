package cz.codecamp.lunchbitch.services.webService;

import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.Restaurant;

import java.util.List;

public class WebServiceImpl implements WebService {

    private LunchMenuDemand searchResults;
    private LunchMenuDemand selectedRestaurants;

    public WebServiceImpl(LunchMenuDemand lunchMenuDemand, LunchMenuDemand selectedRestaurants) {
        this.searchResults = lunchMenuDemand;
	    this.selectedRestaurants = selectedRestaurants;
    }

    @Override
    public List<Restaurant> getFoundRestaurants() {
        return searchResults.getRestaurants();
    }

	@Override
	public List<Restaurant> getSelectedRestaurants() {
		return selectedRestaurants.getRestaurants();
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
				if (restaurant.getRes_id().equals(id)) {
					selectedRestaurants.addRestaurant(restaurant);
				}
			}
		}
	}

	@Override
	public void setLunchMenuDemandEmail(String email) {
		selectedRestaurants.setEmail(email);
	}

	@Override
	public LunchMenuDemand getLunchMenuDemandPreferences() {
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
