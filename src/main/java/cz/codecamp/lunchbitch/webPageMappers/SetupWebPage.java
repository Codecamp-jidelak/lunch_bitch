package cz.codecamp.lunchbitch.webPageMappers;

import cz.codecamp.lunchbitch.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class SetupWebPage {

	private List<String> restaurantIDs;

	public SetupWebPage() {
		restaurantIDs = new ArrayList<>();
	}

	public SetupWebPage(List<Restaurant> restaurants) {
		restaurantIDs = new ArrayList<>();
		for (Restaurant restaurant : restaurants) {
			restaurantIDs.add(restaurant.getId());
		}
	}

	public List<String> getRestaurantIDs() {
		return restaurantIDs;
	}

	public void setRestaurantIDs(List<String> restaurantIDs) {
		this.restaurantIDs = restaurantIDs;
	}
}
