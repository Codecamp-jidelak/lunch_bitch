package cz.codecamp.lunchbitch.controllers.lunchMenuController;


import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.services.lunchMenuService.LunchMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping(value = "/lunch")
public class LunchMenuController {

    private final LunchMenuService lunchMenuService;

    @Autowired
    public LunchMenuController(LunchMenuService lunchMenuService) {
        this.lunchMenuService = lunchMenuService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody HttpStatus saveLunchMenuPreferences() throws IOException {
        List<String> list = new ArrayList<>();
        List<LunchMenuDemand> demands = new ArrayList<>();
        list.add("16506954");
        lunchMenuService.lunchMenuDownload(list, demands);
        return HttpStatus.CREATED;
    }
}
