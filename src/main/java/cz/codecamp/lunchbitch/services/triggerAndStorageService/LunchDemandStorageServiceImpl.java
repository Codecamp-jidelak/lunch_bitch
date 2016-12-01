package cz.codecamp.lunchbitch.services.triggerAndStorageService;

import cz.codecamp.lunchbitch.converters.RestaurantConverters;
import cz.codecamp.lunchbitch.entities.RestaurantInfoEntity;
import cz.codecamp.lunchbitch.entities.UsersRestaurantSelectionEntity;
import cz.codecamp.lunchbitch.models.Location;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.Restaurant;
import cz.codecamp.lunchbitch.repositories.RestaurantInfoRepository;
import cz.codecamp.lunchbitch.repositories.UsersRestaurantSelectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class LunchDemandStorageServiceImpl implements LunchMenuDemandStorageService {

    private final UsersRestaurantSelectionRepository restaurantSelectionRepository;

    private final RestaurantInfoRepository restaurantInfoRepository;

    private final LunchMenuSendingTrigger lunchMenuSendingTrigger;

    @Autowired
    public LunchDemandStorageServiceImpl(LunchMenuSendingTrigger lunchMenuSendingTrigger, UsersRestaurantSelectionRepository restaurantSelectionRepository, RestaurantInfoRepository restaurantInfoRepository) {
        this.lunchMenuSendingTrigger = lunchMenuSendingTrigger;
        this.restaurantSelectionRepository = restaurantSelectionRepository;
        this.restaurantInfoRepository = restaurantInfoRepository;
    }

    @Override
    public LunchMenuDemand getLunchMenuDemand(String email) {
        return null;
    }

    @Override
    @Transactional
    public void deleteLunchMenuDemand(String email) {
        restaurantSelectionRepository.deleteByEmail(email);
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
                .map(RestaurantConverters::convertToRestaurantInfoEntity)
                .collect(toList());
        restaurantInfoRepository.save(newRestaurantInfoEntities);
    }

    private boolean restaurantNotPresentInDatabaseYet(Restaurant restaurant) {
        return !restaurantInfoRepository.exists(restaurant.getId());
    }

    private void storeUsersRestaurantSelections(String email, List<Restaurant> restaurants) {
        List<UsersRestaurantSelectionEntity> newUsersSelections = restaurants
                .stream()
                .map(Restaurant::getId)
                .filter(zomatoId -> selectionNotPresentInDatabaseYet(email, zomatoId))
                .map(zomatoId -> createUsersRestaurantSelections(email, zomatoId))
                .collect(toList());
        restaurantSelectionRepository.save(newUsersSelections);
    }

    private boolean selectionNotPresentInDatabaseYet(String email, String zomatoId) {
        return !restaurantSelectionRepository.selectionExists(email, zomatoId);
    }

    private UsersRestaurantSelectionEntity createUsersRestaurantSelections(String email, String zomatoId) {
        UsersRestaurantSelectionEntity usersRestaurantSelectionEntity = new UsersRestaurantSelectionEntity();
        usersRestaurantSelectionEntity.setEmail(email);
        usersRestaurantSelectionEntity.setZomatoRestaurantId(zomatoId);
        return usersRestaurantSelectionEntity;
    }
}
