package cz.codecamp.lunchbitch.services.lunchMenuService;


import cz.codecamp.lunchbitch.models.LunchMenuDemand;

import java.io.IOException;
import java.util.List;

public interface LunchMenuService {

    /**
     * Download LunchMenuDetail for all restaurants by it's ID
     * @param restaurants id's
     * @param demands to proceed
     */
    void lunchMenuDownload(List<String> restaurants, List<LunchMenuDemand> demands) throws IOException;

}
