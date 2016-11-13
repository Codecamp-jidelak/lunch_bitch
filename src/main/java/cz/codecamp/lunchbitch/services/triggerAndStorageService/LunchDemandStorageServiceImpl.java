package cz.codecamp.lunchbitch.services.triggerAndStorageService;

import cz.codecamp.lunchbitch.models.Location;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.Restaurant;
import cz.codecamp.lunchbitch.services.triggerAndStorageService.storage.entities.RestaurantInfoEntity;
import cz.codecamp.lunchbitch.services.triggerAndStorageService.storage.entities.UsersRestaurantSelectionEntity;
import cz.codecamp.lunchbitch.services.triggerAndStorageService.storage.repositories.RestaurantInfoRepository;
import cz.codecamp.lunchbitch.services.triggerAndStorageService.storage.repositories.UsersRestaurantSelectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class LunchDemandStorageServiceImpl implements LunchMenuDemandStorageService {

	@Autowired
	private UsersRestaurantSelectionRepository restaurantSelection;

	@Autowired
	private RestaurantInfoRepository restaurantInfoRepository;

	@Autowired
	private LunchMenuSendingTrigger lunchMenuSendingTrigger;

	@Override
	public List<LunchMenuDemand> saveLunchDemandAndTriggerAllSending(LunchMenuDemand demand) {
		saveLunchDemand(demand);
		return lunchMenuSendingTrigger.onTrigger();
	}

	@Override
	@Transactional
	public void saveLunchDemand(LunchMenuDemand demand) {
		storePossibleNewRestaurants(demand.getRestaurants());
		storeUsersRestaurantSelections(demand.getEmail(), demand.getRestaurants());
	}


	private void storePossibleNewRestaurants(List<Restaurant> restaurants) {
		List<RestaurantInfoEntity> newRestaurantInfoEntities = restaurants
				.stream()
				.filter(this::restaurantNotPresentInDatabaseYet)
				.map(this::convertToRestaurantInfoEntity)
				.collect(toList());
		restaurantInfoRepository.save(newRestaurantInfoEntities);
	}

	private boolean restaurantNotPresentInDatabaseYet(Restaurant restaurant) {
		return !restaurantInfoRepository.exists(restaurant.getId());
	}

	private RestaurantInfoEntity convertToRestaurantInfoEntity(Restaurant restaurant) {
		Location restaurantsLocation = restaurant.getLocation();

		RestaurantInfoEntity restaurantInfoEntity = new RestaurantInfoEntity();
		restaurantInfoEntity.setZomatoId(restaurant.getId());
		restaurantInfoEntity.setName(restaurant.getName());
		restaurantInfoEntity.setAddress(restaurantsLocation.getAddress());
		restaurantInfoEntity.setLocality(restaurantsLocation.getLocality());
		restaurantInfoEntity.setCity(restaurantsLocation.getCity());
		restaurantInfoEntity.setLatitude(restaurantsLocation.getLatitude());
		restaurantInfoEntity.setLongitude(restaurantsLocation.getLongitude());
		restaurantInfoEntity.setZipcode(restaurantsLocation.getZipcode());
		restaurantInfoEntity.setCountryId(restaurantsLocation.getCountryId());
		return restaurantInfoEntity;
	}

	private void storeUsersRestaurantSelections(String email, List<Restaurant> restaurants) {
		List<UsersRestaurantSelectionEntity> newUsersSelections = restaurants
				.stream()
				.map(Restaurant::getId)
				.filter(zomatoId -> selectionNotPresentInDatabaseYet(email, zomatoId))
				.map(zomatoId -> createUsersRestaurantSelections(email, zomatoId))
				.collect(toList());
		restaurantSelection.save(newUsersSelections);
	}

	private boolean selectionNotPresentInDatabaseYet(String email, String zomatoId) {
		return !restaurantSelection.selectionExists(email, zomatoId);
	}

	private UsersRestaurantSelectionEntity createUsersRestaurantSelections(String email, String zomatoId) {
		UsersRestaurantSelectionEntity usersRestaurantSelectionEntity = new UsersRestaurantSelectionEntity();
		usersRestaurantSelectionEntity.setEmail(email);
		usersRestaurantSelectionEntity.setZomatoRestaurantId(zomatoId);
		return usersRestaurantSelectionEntity;
	}
}
