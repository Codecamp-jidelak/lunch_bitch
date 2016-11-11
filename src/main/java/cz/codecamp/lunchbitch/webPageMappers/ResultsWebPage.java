package cz.codecamp.lunchbitch.webPageMappers;

import java.util.ArrayList;
import java.util.List;

public class ResultsWebPage {

	private List<String> restaurantIDs;

	public ResultsWebPage() {
		restaurantIDs = new ArrayList<>();
	}

	public List<String> getRestaurantIDs() {
		return restaurantIDs;
	}

	public void setRestaurantIDs(List<String> restaurantIDs) {
		this.restaurantIDs = restaurantIDs;
	}
}
