package cz.codecamp.lunchbitch.repositories;

import cz.codecamp.lunchbitch.entities.UsersRestaurantSelectionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersRestaurantSelectionRepository extends CrudRepository<UsersRestaurantSelectionEntity, Long> {


    @Query("SELECT count(s)>0 FROM RestaurantSelection s WHERE s.email = :email AND s.zomatoRestaurantId = :zomatoId")
    boolean selectionExists(@Param("email") String email, @Param("zomatoId") String zomatoRestaurantId);

    void deleteByEmail(String email);
    
    @Query("SELECT s.zomatoRestaurantId FROM RestaurantSelection s WHERE s.email = :email")
    List<Long> findZomatoRestaurantIdsByEmail(@Param("email") String email);
}
