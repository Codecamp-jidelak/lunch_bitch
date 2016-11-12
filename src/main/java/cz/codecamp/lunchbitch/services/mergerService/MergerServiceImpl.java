package cz.codecamp.lunchbitch.services.mergerService;

import cz.codecamp.lunchbitch.models.LunchMenu;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.Restaurant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MergerServiceImpl implements MergerService {


    @Override
    public List<LunchMenuDemand> mergeLunchMenusWithRestaurants(Map<String, LunchMenu> lunchMenuMap, List<LunchMenuDemand> demands) {

        for(LunchMenuDemand demand: demands){

            for(Restaurant restaurant: demand.getRestaurants()){

                restaurant.setLunchmenu(lunchMenuMap.get(restaurant.getId()));

            }

        }
        return demands;
    }
}
