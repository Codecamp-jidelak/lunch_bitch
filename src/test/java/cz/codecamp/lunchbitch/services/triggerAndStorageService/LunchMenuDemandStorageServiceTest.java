package cz.codecamp.lunchbitch.services.triggerAndStorageService;

import cz.codecamp.lunchbitch.models.Location;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.Restaurant;
import cz.codecamp.lunchbitch.repositories.RestaurantInfoRepository;
import cz.codecamp.lunchbitch.repositories.UsersRestaurantSelectionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LunchMenuDemandStorageServiceTest {

	@Autowired
	LunchMenuDemandStorageService storageService;

	@Autowired
	RestaurantInfoRepository restaurantRepository;

	@Autowired
	UsersRestaurantSelectionRepository selectionRepository;

	@Before
	public void cleanDatabase() {
		restaurantRepository.deleteAll();
		selectionRepository.deleteAll();
	}

	@Test
	public void lunchMenuDemandsAreStored() {
		LunchMenuDemand lunchMenuDemand = new LunchMenuDemand();
		lunchMenuDemand.setEmail("peternikodemjr@gmail.com");

		Restaurant restaurant = new Restaurant();
		restaurant.setId("16521093");
		restaurant.setName("kozlovna1");
		restaurant.setLocation(new Location());

		Restaurant restaurant2 = new Restaurant();
		restaurant2.setId("16508973");
		restaurant2.setName("kozlovna2");
		restaurant2.setLocation(new Location());
		lunchMenuDemand.setRestaurants(Arrays.asList(restaurant, restaurant2));

		List<LunchMenuDemand> lunchMenuDemands = storageService.saveLunchDemandAndTriggerAllSending(lunchMenuDemand);

		assertEquals(restaurantRepository.count(), 2);
		assertEquals(lunchMenuDemands.get(0).getEmail(), "peternikodemjr@gmail.com");
		assertEquals(lunchMenuDemands.get(0).getRestaurants().size(), 2);
	}

}