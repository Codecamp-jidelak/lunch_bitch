package cz.codecamp.lunchbitch.repositories;

import cz.codecamp.lunchbitch.entities.RestaurantInfoEntity;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantInfoRepository extends CrudRepository<RestaurantInfoEntity, String> {
}
