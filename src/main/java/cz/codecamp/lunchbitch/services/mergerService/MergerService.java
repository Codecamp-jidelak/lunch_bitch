package cz.codecamp.lunchbitch.services.mergerService;


import cz.codecamp.lunchbitch.models.LunchMenu;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;

import java.util.List;
import java.util.Map;

public interface MergerService {

    List<LunchMenuDemand> mergeLunchMenusWithRestaurants(Map<String, LunchMenu> lunchMenuMap, List<LunchMenuDemand> demands);

}
