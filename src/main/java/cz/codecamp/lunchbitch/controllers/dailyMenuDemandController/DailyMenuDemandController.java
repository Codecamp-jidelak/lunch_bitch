package cz.codecamp.lunchbitch.controllers.dailyMenuDemandController;


import cz.codecamp.lunchbitch.models.DataModel;
import cz.codecamp.lunchbitch.services.dailyMenuDemandService.DailyMenuDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@SuppressWarnings("unused")
@RestController
@RequestMapping(value = "/menu")
public class DailyMenuDemandController {

    private final DailyMenuDemandService service;

    @Autowired
    public DailyMenuDemandController(DailyMenuDemandService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody HttpStatus searchRestaurant(@RequestBody DataModel dataModel) throws IOException {
        return service.saveDailyMenuPreferences(dataModel);
    }

}
