package cz.codecamp.lunchbitch.services.lunchMenuService;


import cz.codecamp.lunchbitch.models.LunchMenu;
import cz.codecamp.lunchbitch.models.LunchMenuDemand;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface LunchMenuService {

    /**
     * Download LunchMenuDetail for all restaurants by it's ID
     * @param restaurants id's
     * @param demands to proceed
     */
    Map<String, LunchMenu> lunchMenuDownload(List<String> restaurants, List<LunchMenuDemand> demands) throws IOException;

}
