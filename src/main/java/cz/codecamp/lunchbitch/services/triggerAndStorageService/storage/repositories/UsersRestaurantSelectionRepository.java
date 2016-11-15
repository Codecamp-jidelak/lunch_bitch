package cz.codecamp.lunchbitch.services.triggerAndStorageService.storage.repositories;

import cz.codecamp.lunchbitch.services.triggerAndStorageService.storage.entities.UsersRestaurantSelectionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UsersRestaurantSelectionRepository extends CrudRepository<UsersRestaurantSelectionEntity, Long> {


	@Query("SELECT count(s)>0 FROM RestaurantSelection s WHERE s.email = :email AND s.zomatoRestaurantId = :zomatoId")
	boolean selectionExists(@Param("email") String email, @Param("zomatoId") String zomatoRestaurantId);
}
