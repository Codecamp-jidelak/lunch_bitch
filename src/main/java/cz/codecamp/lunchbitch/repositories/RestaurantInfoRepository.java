package cz.codecamp.lunchbitch.repositories;

import cz.codecamp.lunchbitch.entities.RestaurantInfoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RestaurantInfoRepository extends CrudRepository<RestaurantInfoEntity, String> {
    List<RestaurantInfoEntity> findByZomatoIdIn(List<Long> restaurantIds);
}
