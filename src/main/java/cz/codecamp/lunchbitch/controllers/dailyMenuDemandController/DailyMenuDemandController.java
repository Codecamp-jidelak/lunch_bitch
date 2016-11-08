package cz.codecamp.lunchbitch.controllers.dailyMenuDemandController;

import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.services.lunchMenuDemandService.LunchMenuDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@SuppressWarnings("unused")
@RestController
@RequestMapping(value = "/menu")
public class DailyMenuDemandController {

    private final LunchMenuDemandService service;

    @Autowired
    public DailyMenuDemandController(LunchMenuDemandService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody HttpStatus saveLunchMenuPreferences(@RequestBody LunchMenuDemand dataModel) throws IOException {
        service.saveLunchMenuPreferences(dataModel);
        return HttpStatus.CREATED;
    }

}
