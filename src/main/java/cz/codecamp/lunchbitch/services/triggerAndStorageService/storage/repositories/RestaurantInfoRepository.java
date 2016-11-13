package cz.codecamp.lunchbitch.services.triggerAndStorageService.storage.repositories;

import cz.codecamp.lunchbitch.services.triggerAndStorageService.storage.entities.RestaurantInfoEntity;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantInfoRepository extends CrudRepository<RestaurantInfoEntity, String> {
}
